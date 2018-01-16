package edu.nju.mailbox.controller;

import edu.nju.paper.PaperApplication;
import edu.nju.paper.bean.PaperReBean;
import edu.nju.paper.controller.PaperController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PaperApplication.class)
public class PaperControllerTest {

    @Autowired
    private PaperController paperController;

    @Test
    public void testGeneratePapers(){
        List<String> students = new ArrayList<>();
        students.add("00002");
        PaperReBean paperReBean = paperController.generatePapersBeforeTest("2017121501", "00001", students);
        Assert.assertEquals(0, paperReBean.getRcode());
    }


}
