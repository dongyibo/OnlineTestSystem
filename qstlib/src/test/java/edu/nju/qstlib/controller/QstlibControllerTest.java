package edu.nju.qstlib.controller;

import edu.nju.qstlib.bean.RcodeBean;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QstlibControllerTest {

    @Autowired
    private QstlibController qstlibController;

    @Test
    public void testImportLib(){
        MultipartFile file = transferFile(new File("src/test/testlib.xls"));
        RcodeBean rcode = qstlibController.importLib("00001", "学霸宝典", file);
        Assert.assertEquals(0, rcode.getRcode());
    }

    private MultipartFile transferFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return new MockMultipartFile("file", file.getName(), "text/plain", fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
