package edu.nju.test.controller;

import edu.nju.test.bean.*;
import edu.nju.test.exception.PaperFullException;
import edu.nju.test.exception.TimeExceededException;
import edu.nju.test.exception.TimeInvalidException;
import edu.nju.test.exception.WrongTestCodeException;
import edu.nju.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value="/test")
public class TestController {
    @Autowired
    TestService testService;
    @RequestMapping(value={""},method= RequestMethod.GET)
    public ReTestBean getTestInfo(@RequestParam(value="userId")String userId, @RequestParam(value="testCode",required = true)String testCode){
        ReTestBean test;
        try {
            test = new ReTestBean(0, testService.getTest(testCode));
        }catch (WrongTestCodeException e){
            return new ReTestBean(1,null);
        } catch (PaperFullException e) {
            return new ReTestBean(0x300, null);
        } catch (TimeInvalidException e) {
            return new ReTestBean(0x301, null);
        }
        return test;
    }

    @RequestMapping(value={""},method=RequestMethod.POST)
    public ReTestResultBean submitTest(@RequestBody UserTestBean userTestBean){
        ReTestResultBean result;
        try{
            TestResultBean re = testService.getTestResult(userTestBean);
            if (re == null){
                return new ReTestResultBean(4, null);
            }
            result = new ReTestResultBean(0,re);
            testService.saveTestResult(re);
        }catch (TimeExceededException e){
            return new ReTestResultBean(1,null);
        }
        return result;
    }

    @RequestMapping(value = "/{order}", method = RequestMethod.GET)
    public QuestionReBean getTestQue(@RequestParam(value = "testCode") String testCode, @PathVariable("order") int order){
        try {
            QuestionBean questionBean = testService.getTestQue(testCode, order);
            return new QuestionReBean(0, questionBean);
        } catch (WrongTestCodeException e){
            return new QuestionReBean(1, null);
        }
    }

}
