package edu.nju.testman.controller;

import edu.nju.testman.bean.*;
import edu.nju.testman.service.TestManService;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/test-man")
public class TestManController {

    private final TestManService testManService;

    @Autowired
    public TestManController(TestManService testManService) {
        this.testManService = testManService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public RcodeBean generateTest(@RequestBody TestInBean testBean){
        return new RcodeBean(testManService.generateTest(testBean));
    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    public RcodeBean changeTest(@RequestBody TestInBean testBean){
        return new RcodeBean(testManService.changeTest(testBean));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TestListBean getTestList(@RequestParam("userId") String userId){
        List<TestNameBean> tests = testManService.getTestList(userId);
        return new TestListBean(0, tests);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TestReBean getTest(@PathVariable("id") String id){
        TestBean testBean = testManService.getTest(id);
        return new TestReBean(0, testBean);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RcodeBean deleteTest(@PathVariable("id") String id){
        return new RcodeBean(testManService.deleteTest(id));
    }

}
