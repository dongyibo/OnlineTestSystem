package edu.nju.testman.controller;

import edu.nju.testman.bean.*;
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
import java.util.Calendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestManControllerTest {

    @Autowired
    private TestManController controller;

    @Test
    public void testAGenerateTest(){
        TestInBean testInBean = new TestInBean();
        testInBean.setUserId("00001");
        testInBean.setLibId("0000100");
        testInBean.setTestName("膜法三级");
        testInBean.setQuesNum(3);
        Calendar startTime = Calendar.getInstance();
        startTime.set(2017, Calendar.DECEMBER, 19, 14,0,0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, Calendar.DECEMBER, 19, 16, 0, 0);
        testInBean.setStartTime(startTime);
        testInBean.setEndTime(endTime);
        List<QIDScoreBean> questions = new ArrayList<>();
        QIDScoreBean q1 = new QIDScoreBean("00001000000", "1+1", new BigDecimal(10));
        QIDScoreBean q2 = new QIDScoreBean("00001000001", "2+2", new BigDecimal(50));
        QIDScoreBean q3 = new QIDScoreBean("00001000002", "3+3", new BigDecimal(40));
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        testInBean.setQuestions(questions);
        List<StudentBean> students = new ArrayList<>();
        students.add(new StudentBean("839754614@qq.com", "鱼王", "141250195", "4", "4"));
        testInBean.setStudents(students);
        controller.generateTest(testInBean);
    }

    @Test
    public void testBChangeTest(){
        TestInBean testInBean = new TestInBean();
        testInBean.setUserId("00001");
        testInBean.setLibId("0000100");
        testInBean.setTestId("2017121500");
        testInBean.setTestName("膜法二级");
        Calendar startTime = Calendar.getInstance();
        startTime.set(2017, Calendar.DECEMBER, 15, 14,0,0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, Calendar.DECEMBER, 15, 17, 0, 0);
        testInBean.setStartTime(startTime);
        testInBean.setEndTime(endTime);
        List<QIDScoreBean> questions = new ArrayList<>();
        QIDScoreBean q1 = new QIDScoreBean("00001000000", "1+1", new BigDecimal(10));
        QIDScoreBean q2 = new QIDScoreBean("00001000001", "1+1", new BigDecimal(50));
        QIDScoreBean q3 = new QIDScoreBean("00001000002", "1+1", new BigDecimal(40));
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        testInBean.setQuestions(questions);
        List<StudentBean> students = new ArrayList<>();
        students.add(new StudentBean("502101600@qq.com", "鱼王", "141250195", "4", "4"));
        testInBean.setStudents(students);
        //controller.changeTest(testInBean);
    }

    @Test
    public void testCGetTestList(){
        TestListBean testListBean = controller.getTestList("00001");
        Assert.assertEquals(0, testListBean.getRcode());
        List<TestNameBean> tests = testListBean.getTests();
        TestNameBean test1 = tests.get(0);
        Assert.assertEquals("2017121500", test1.getId());
        Assert.assertEquals("膜法二级", test1.getName());
    }

    @Test
    public void testDGetTest(){
        TestReBean testResult = controller.getTest("2017121500");
        Assert.assertEquals(0, testResult.getRcode());
        TestBean test = testResult.getTest();
        Assert.assertEquals("2017121500", test.getTestId());
        Assert.assertEquals("00001", test.getUserId());
        Assert.assertEquals("0000100", test.getLibId());
        List<StudentReBean> students = test.getStudents();
        Assert.assertEquals("鱼王", students.get(0).getName());
        Assert.assertEquals("00002", students.get(0).getId());
        List<QIDScoreBean> questions = test.getQuestions();
        for (QIDScoreBean q:questions) {
            System.out.println(q.getContent());
        }
    }

//    @Test
//    public void testEDeleteTest(){
//        RcodeBean rcodeBean = controller.deleteTest("2017121501");
//        Assert.assertEquals(0, rcodeBean.getRcode());
//    }

    @Test
    public void testFGenerateTest(){
        TestInBean testInBean = new TestInBean();
        testInBean.setUserId("00001");
        testInBean.setLibId("0000100");
        testInBean.setTestName("膜法三级");
        testInBean.setQuesNum(3);
        Calendar startTime = Calendar.getInstance();
        startTime.set(2017, Calendar.DECEMBER, 19, 14,0,0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, Calendar.DECEMBER, 19, 16, 0, 0);
        testInBean.setStartTime(startTime);
        testInBean.setEndTime(endTime);
        List<QIDScoreBean> questions = new ArrayList<>();
        QIDScoreBean q1 = new QIDScoreBean("00001000000", "1+1", new BigDecimal(10));
        QIDScoreBean q2 = new QIDScoreBean("00001000001", "2+2", new BigDecimal(50));
        QIDScoreBean q3 = new QIDScoreBean("00001000002", "3+3", new BigDecimal(30));
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        testInBean.setQuestions(questions);
        List<StudentBean> students = new ArrayList<>();
        students.add(new StudentBean("839754614@qq.com", "鱼王", "141250195", "4", "4"));
        testInBean.setStudents(students);
        RcodeBean rcodeBean = controller.generateTest(testInBean);
        Assert.assertEquals(0x203, rcodeBean.getRcode());
    }
}
