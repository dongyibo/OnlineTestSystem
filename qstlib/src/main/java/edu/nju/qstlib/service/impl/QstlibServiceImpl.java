package edu.nju.qstlib.service.impl;

import edu.nju.qstlib.bean.LibListBean;
import edu.nju.qstlib.dao.QstLibDAO;
import edu.nju.qstlib.dao.QuestionDAO;
import edu.nju.qstlib.domain.Option;
import edu.nju.qstlib.domain.QstLib;
import edu.nju.qstlib.domain.Question;
import edu.nju.qstlib.exception.QstLibFullException;
import edu.nju.qstlib.service.QstlibService;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class QstlibServiceImpl implements QstlibService {
    private final QstLibDAO qstLibDAO;

    @Autowired
    public QstlibServiceImpl(QstLibDAO qstLibDAO, QuestionDAO questionDAO) {
        this.qstLibDAO = qstLibDAO;
    }

    @Override
    public int importQstlib(String userId, String libName, MultipartFile file) {
        try {
            Workbook workbook;
            if (file.getOriginalFilename().endsWith("xlsx")){
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (file.getOriginalFilename().endsWith("xls")){
                workbook = new HSSFWorkbook(file.getInputStream());
            } else {
                return 3;
            }
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null){
                return 0x102;
            }
            QstLib qstLib = new QstLib();
            qstLib.setUserId(userId);
            try {
                qstLib.setId(calcuLibId(userId));
            } catch (QstLibFullException e) { // 题库越界直接返回错误码
                return 0x100;
            }

            Set<Question> questions = new HashSet<>();
            int first = sheet.getFirstRowNum();
            int last = sheet.getLastRowNum(); // 获得首尾行
            if (last < 1){
                return 3;
            }
            // 第一行是题库名
            Row nameRow = sheet.getRow(0);
            Cell nameCell = nameRow.getCell(0);
            nameCell.setCellType(CellType.STRING);
            qstLib.setName(nameCell.getStringCellValue());
            // 之后的行是题目
            boolean isFull = (last - first) > 10000; // 总数超过一万行则越界，后面的题目舍弃
            for (int j = first + 1;j <= last;j++){ // 逐行读取数据，每一行是一个问题
                if (j - first > 10000){ // 越界直接跳出
                    break;
                }
                Row row = sheet.getRow(j);
                if (row == null){
                    continue;
                }
                int lastCell = row.getLastCellNum(); // 获得列数
                if (lastCell < 2){ // 至少有一个题干和一个选项才是合法题目
                    continue;
                }
                Question question = new Question();
                question.setLibId(qstLib.getId());
                question.setId(qstLib.getId() + String.format("%04d", j - first));
                Set<Option> options = new HashSet<>();
                for (int k = 0; k < lastCell;k++){ // 从第一列开始读取，第一列是题目，后面是选项
                    Cell cell = row.getCell(k);
                    cell.setCellType(CellType.STRING);
                    if (k == 0){
                        String content = cell.getStringCellValue();
                        if (content.isEmpty()){
                            break;
                        }
                        question.setContent(content);
                    } else {
                        Option option = new Option();
                        option.setName(cell.getStringCellValue());
                        option.setId(k - 1); // 选项编号从0开始
                        option.setQuesId(question.getId());
                        // 字体标红表示为正确答案，否则为错误答案
                        if (workbook instanceof XSSFWorkbook){
                            XSSFFont font = (XSSFFont) workbook.getFontAt(cell.getCellStyle().getFontIndex());
                            XSSFColor color = font.getXSSFColor();
                            byte[] argb = color.getARGB();
                            if (argb[0] == -1 && argb[1] == -1 && argb[2] == 0 && argb[3] == 0){
                                option.setRight(true);
                            } else {
                                option.setRight(false);
                            }
                        } else {
                            HSSFFont font = (HSSFFont) workbook.getFontAt(cell.getCellStyle().getFontIndex());
                            HSSFColor color = font.getHSSFColor((HSSFWorkbook) workbook);
                            short[] rgb = color.getTriplet();
                            if (rgb[0] == 255 && rgb[1] == 0 && rgb[2] == 0){
                                option.setRight(true);
                            } else {
                                option.setRight(false);
                            }
                        }
                        options.add(option);
                    }
                }
                question.setOptions(options);
                questions.add(question);
            }
            qstLib.setQuestions(questions);
            qstLibDAO.save(qstLib);
            if (isFull){
                return 0x101;
            } else {
                return 0;
            }
        } catch (IOException e) {
            return 3;
        }
    }

    private String calcuLibId(String userId) throws QstLibFullException {
        List<QstLib> qstLibs = qstLibDAO.findByUserId(userId);
        if (qstLibs.size() == 0){ // 无库，新建为00
            return userId + "00";
        }
        int lastNum = -1;
        for (QstLib lib:qstLibs) { // 对库ID遍历，有空则插入，否则放到最后，超出100个抛异常
            String id = lib.getId();
            String idNum = id.substring(5);
            int nowNum = Integer.parseInt(idNum);
            if (nowNum != lastNum + 1){
                return userId + String.format("%02d", lastNum + 1);
            } else {
                ++lastNum;
            }
        }
        // 跳出表示没有插空
        if (lastNum >= 99){
            throw new QstLibFullException();
        } else {
            return userId + String.format("%02d", lastNum + 1);
        }
    }

    @Override
    public List<LibListBean> getLibs(String userId) {
        List<QstLib> qstLibs = qstLibDAO.findByUserId(userId);
        List<LibListBean> result = new ArrayList<>();
        for (QstLib qstlib:qstLibs) {
            LibListBean bean = new LibListBean(qstlib.getId(), qstlib.getName());
            result.add(bean);
        }
        return result;
    }

    @Override
    public List<LibListBean> changeLib(String libId, String libName) {
        qstLibDAO.updateLibName(libId, libName);
        QstLib lib = qstLibDAO.findById(libId);
        return getLibs(lib.getUserId());
    }

    @Override
    public List<LibListBean> deleteLib(String libId) {
        QstLib lib = qstLibDAO.findById(libId);
        String userId = lib.getUserId();
        qstLibDAO.delete(libId);
        return getLibs(userId);
    }
}
