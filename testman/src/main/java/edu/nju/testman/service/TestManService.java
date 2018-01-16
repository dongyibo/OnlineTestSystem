package edu.nju.testman.service;

import edu.nju.testman.bean.TestBean;
import edu.nju.testman.bean.TestInBean;
import edu.nju.testman.bean.TestNameBean;

import java.util.List;

public interface TestManService {
    int generateTest(TestInBean testBean);

    int changeTest(TestInBean testBean);

    List<TestNameBean> getTestList(String userId);

    TestBean getTest(String id);

    int deleteTest(String id);
}
