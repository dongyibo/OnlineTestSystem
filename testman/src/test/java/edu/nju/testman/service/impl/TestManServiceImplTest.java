package edu.nju.testman.service.impl;

import edu.nju.testman.dao.TestManDAO;
import edu.nju.testman.domain.Participate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestManServiceImplTest {
    @Autowired
    private TestManDAO testManDAO;

    @Test
    public void testInsert(){
        edu.nju.testman.domain.Test test = new edu.nju.testman.domain.Test();
        test.setId("2017121500");
        test.setLibId("0000100");
        test.setCreator("00001");
        test.setQuesNum(3);
        test.setSubject("膜法三级");
        Calendar startTime = Calendar.getInstance();
        startTime.set(2017, Calendar.DECEMBER, 15, 14,0,0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, Calendar.DECEMBER, 15, 16, 0, 0);
        test.setStartTime(startTime);
        test.setEndTime(endTime);
        Set<Participate> participateSet = new HashSet<>();
        Participate participate = new Participate();
        participate.setTestId(test.getId());
        participate.setPassword("12314123");
        participate.setEmail("839754614@qq.com");
        participate.setStudentId("141250195");
        participate.setName("鱼王");
        participate.setGrade("4");
        participate.setClassName("4");
        participate.setScore(BigDecimal.ZERO);
        participate.setUserId("00002");
        participateSet.add(participate);
        test.setParticipateSet(participateSet);
        testManDAO.save(test);
    }
}
