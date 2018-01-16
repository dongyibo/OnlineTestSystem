package edu.nju.statistics.controller;

import edu.nju.statistics.bean.*;
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
public class StatisticsControllerTest {

    @Autowired
    private StatisticsController statisticsController;

    @Test
    public void testAGenerateStatistics(){
        RcodeBean rcodeBean = statisticsController.generateStatistics("2017121500");
        Assert.assertEquals(0, rcodeBean.getRcode());
    }

    @Test
    public void testBCheckStatistics(){
        StatisticsReBean result = statisticsController.checkStatistics("2017121500", "00002");
        Assert.assertEquals(2, result.getRcode());
        StatisticsReBean result2 = statisticsController.checkStatistics("2017121500", "00001");
        Assert.assertEquals(0, result2.getRcode());
        StatisticsBean statistics = result2.getStatistics();
        Assert.assertEquals("2017121500", statistics.getTestId());
        List<ScoreBean> scoreBeans = statistics.getScores();
        ScoreBean score = scoreBeans.get(0);
        Assert.assertEquals(new BigDecimal(50), score.getScore());
        Assert.assertEquals("鱼王", score.getName());
    }

    @Test
    public void testCGeneratePapers(){
        List<String> students = new ArrayList<>();
        students.add("00002");
        PaperReBean paperReBean = statisticsController.generatePapers("2017121500", "00002", students);
        Assert.assertEquals(2, paperReBean.getRcode());
        PaperReBean result = statisticsController.generatePapers("2017121500", "00001", students);
        Assert.assertEquals(0, result.getRcode());
        PaperBean paperBean = result.getPaper();
        List<SinglePaperBean> singlePaperBeans = paperBean.getPapers();
        SinglePaperBean singlePaperBean = singlePaperBeans.get(0);
        Assert.assertEquals("141250195", singlePaperBean.getStudentId());
        List<QuestionBean> questionBeans = singlePaperBean.getQuestions();
        for (QuestionBean ques:questionBeans) {
            System.out.println(ques.getContent());
            System.out.println(ques.getOrder());
            System.out.println(ques.getScore());
        }
    }
}
