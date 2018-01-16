package edu.nju.paper.service;

import edu.nju.paper.bean.PaperBean;
import edu.nju.paper.exception.PaperFullException;
import edu.nju.paper.exception.TestEndException;

import java.util.List;

/**
 * Created by dongyibo on 2017/11/27.
 */
public interface PaperService {

    /**
     * 为考生考前生成试卷
     * @param testId
     * @param teacher
     * @param students
     * @return
     */
    public PaperBean generatePapers(String testId, String teacher, List<String> students) throws PaperFullException, TestEndException;
}
