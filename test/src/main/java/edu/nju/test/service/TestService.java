package edu.nju.test.service;

import edu.nju.test.bean.QuestionBean;
import edu.nju.test.bean.TestBean;
import edu.nju.test.bean.TestResultBean;
import edu.nju.test.bean.UserTestBean;
import edu.nju.test.exception.PaperFullException;
import edu.nju.test.exception.TimeExceededException;
import edu.nju.test.exception.TimeInvalidException;
import edu.nju.test.exception.WrongTestCodeException;

public interface TestService {
        TestBean getTest(String testCode) throws WrongTestCodeException, PaperFullException, TimeInvalidException;
        TestResultBean getTestResult(UserTestBean userTestBean) throws TimeExceededException;
        boolean saveTestResult(TestResultBean testResult);
        QuestionBean getTestQue(String testCode, int order) throws WrongTestCodeException;
}
