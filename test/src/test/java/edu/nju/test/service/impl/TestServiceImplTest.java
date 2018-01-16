package edu.nju.test.service.impl;

import edu.nju.test.bean.QuestionBean;
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
public class TestServiceImplTest {
    @Autowired
    private TestServiceImpl t;
    @Test
    public void generateQuestions() throws Exception {
        List<QuestionBean> l = new ArrayList<QuestionBean>();
        int[] sc ={1,2,3,4,5,97};
        for(int i=0;i<6;i++){
            l.add(new QuestionBean(i, 0,null,null,null,new BigDecimal(sc[i])));
        }
        l=t.generateQuestions(l,2);
        for(QuestionBean qb:l){
            System.out.println(qb.getScore());
        }
    }

}