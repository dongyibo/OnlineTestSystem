package edu.nju.qstlib.controller;

import edu.nju.qstlib.bean.*;
import edu.nju.qstlib.domain.QstLib;
import edu.nju.qstlib.exception.QstLibFullException;
import edu.nju.qstlib.exception.QuestionFullException;
import edu.nju.qstlib.service.QstlibService;
import edu.nju.qstlib.service.QuestionService;
import io.swagger.annotations.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@Api(value = "Qstlib", description = "题库管理", tags = "题库管理接口")
@RestController
@RequestMapping("/qstlib")
public class QstlibController {

    private final QstlibService qstlibService;
    private final QuestionService questionService;

    @Autowired
    public QstlibController(QstlibService qstlibService, QuestionService questionService) {
        this.qstlibService = qstlibService;
        this.questionService = questionService;
    }


    @ApiOperation(value = "导入题库", notes = "将传送来的excel文档作为题库导入数据库")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
                        @ApiImplicitParam(name = "libName", value = "题库名", required = true, dataType = "String"),
                        @ApiImplicitParam(name = "file", value = "xlsx文件二进制串", required = true, dataType = "byte[]")})
    @ApiResponses({@ApiResponse(code = 0, message = "成功"), @ApiResponse(code = 3, message = "文件格式错误"),
    @ApiResponse(code = 0x100, message = "题库数量已满"), @ApiResponse(code = 0x101, message = "题目数量越界"),
    @ApiResponse(code = 0x102, message = "没有读取到表格")})
    @RequestMapping(value = "/lib", method = RequestMethod.POST)
    public RcodeBean importLib(@RequestParam("userId") String userId, @RequestParam("libName") String libName,
                               @RequestParam("file") MultipartFile file){
        int rcode = qstlibService.importQstlib(userId, libName, file);
        RcodeBean rcodeBean = new RcodeBean();
        rcodeBean.setRcode(rcode);
        return rcodeBean;
    }

    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/lib", method = RequestMethod.GET)
    public QstLibBean libList(@RequestParam("userId") String userId){
        List<LibListBean> libList = qstlibService.getLibs(userId);
        QstLibBean qstLibBean = new QstLibBean();
        qstLibBean.setLibs(libList);
        qstLibBean.setRcode(0);
        return qstLibBean;
    }

    @RequestMapping(value = "/lib/{libId}", method = RequestMethod.PUT)
    public QstLibBean changeLib(@PathVariable String libId, @RequestParam("libName") String libName){
        List<LibListBean> libList = qstlibService.changeLib(libId, libName);
        QstLibBean qstLibBean = new QstLibBean();
        qstLibBean.setLibs(libList);
        qstLibBean.setRcode(0);
        return qstLibBean;
    }

    @RequestMapping(value = "/lib/{libId}", method = RequestMethod.DELETE)
    public QstLibBean deleteLib(@PathVariable String libId){
        List<LibListBean> libList = qstlibService.deleteLib(libId);
        QstLibBean qstLibBean = new QstLibBean();
        qstLibBean.setLibs(libList);
        qstLibBean.setRcode(0);
        return qstLibBean;
    }

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public QuesListReBean questionList(@RequestParam("libId") String libId){
        List<QuesBean> quesList = questionService.getQuestionList(libId);
        QuesListReBean result = new QuesListReBean();
        result.setQuestions(quesList);
        result.setRcode(0);
        return result;
    }

    @RequestMapping(value = "/question/{questionId}", method = RequestMethod.GET)
    public QuesReBean getQuestion(@PathVariable String questionId){
        QuesBean quesBean = questionService.getQuestion(questionId);
        QuesReBean result = new QuesReBean();
        result.setQuestion(quesBean);
        result.setRcode(0);
        return result;
    }

    @RequestMapping(value = "/question/{questionId}", method = RequestMethod.PUT)
    public QuesListReBean changeQuestion(@PathVariable String questionId, @RequestBody QuesBean question) {
        List<QuesBean> quesList = questionService.changeQuestion(questionId, question);
        QuesListReBean result = new QuesListReBean();
        result.setRcode(0);
        result.setQuestions(quesList);
        return result;
    }

    @RequestMapping(value = "/question/{questionId}", method = RequestMethod.DELETE)
    public QuesListReBean deleteQuestion(@PathVariable String questionId){
        List<QuesBean> quesList = questionService.deleteQuestion(questionId);
        QuesListReBean result = new QuesListReBean();
        result.setQuestions(quesList);
        result.setRcode(0);
        return result;
    }
}
