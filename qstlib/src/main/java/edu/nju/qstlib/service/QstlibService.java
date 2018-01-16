package edu.nju.qstlib.service;

import edu.nju.qstlib.bean.LibListBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 题库相关操作Service
 */
public interface QstlibService {
    /**
     * 导入题库
     * @param userId 用户ID
     * @param libName 题库名
     * @param file excel文件二进制
     * @return 结果码
     */
    public int importQstlib(String userId, String libName, MultipartFile file);

    /**
     * 查看用户题库列表
     * @param userId 用户ID
     * @return 题库名和ID列表
     */
    public List<LibListBean> getLibs(String userId);

    /**
     * 修改题库名
     * @param libId 题库ID
     * @param libName 题库新名称
     * @return 题库名和ID列表
     */
    public List<LibListBean> changeLib(String libId, String libName);

    /**
     * 删除题库
     * @param libId 题库ID
     * @return 题库名和ID列表
     */
    public List<LibListBean> deleteLib(String libId);
}
