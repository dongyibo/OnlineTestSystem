package edu.nju.test.controller;

import edu.nju.test.bean.*;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestControllerTest {

    @Autowired
    private TestController testController;

    @Test
    public void testGetTestInfo(){
        ReTestBean testBean = testController.getTestInfo("00002", "ecazbbxbbieqbdzbehfq");
        Assert.assertEquals(0, testBean.getRcode());
        TestBean test = testBean.getTest();
        Assert.assertEquals(3, test.getQuesNum());
        Assert.assertEquals("膜法一级", test.getTestName());
        Assert.assertEquals("2017121501", test.getTestId());
        QuestionBean questionBean = test.getQuestion();
        System.out.println(questionBean.getDescription());
        System.out.println(questionBean.getId());
    }

    @Test
    public void testGetTestQue(){
        QuestionReBean questionReBean = testController.getTestQue("ecazbbxbbieqbdzbehfq", 1);
        Assert.assertEquals("00001000002", questionReBean.getQuestion().getId());
    }

//    @Test
    public void testSubmitTest(){
        List<AnswerBean> questionBeans = new ArrayList<>();
        // set q1
        AnswerBean q1 = new AnswerBean();
        q1.setId("00001000000");
        List<Integer> o1 = new ArrayList<>();
        o1.add(-1);
        q1.setOptions(o1);
        // set q2
        AnswerBean q2 = new AnswerBean();
        q2.setId("00001000001");
        List<Integer> o2 = new ArrayList<>();
        o2.add(3);
        q2.setOptions(o2);
        // set q3
        AnswerBean q3 = new AnswerBean();
        q3.setId("00001000002");
        List<Integer> o3 = new ArrayList<>();
        o3.add(2);
        o3.add(3);
        q3.setOptions(o3);
        // set questions
        questionBeans.add(q1);
        questionBeans.add(q2);
        questionBeans.add(q3);


        UserTestBean userTestBean = new UserTestBean("00002", "2017121501", questionBeans);
        ReTestResultBean result = testController.submitTest(userTestBean);
        TestResultBean testResultBean = result.getTestResult();
        Assert.assertEquals(0, result.getRcode());
        Assert.assertEquals(new BigDecimal(50), testResultBean.getScore());
    }

}
