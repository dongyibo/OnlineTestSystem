package edu.nju.paper.controller;

import edu.nju.paper.bean.PaperBean;
import edu.nju.paper.bean.PaperReBean;
import edu.nju.paper.exception.PaperFullException;
import edu.nju.paper.exception.TestEndException;
import edu.nju.paper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dongyibo on 2017/11/27.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/paper")
public class PaperController {

    private final PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public PaperReBean generatePapersBeforeTest(@RequestParam("testId") String testId,
                                                @RequestParam("teacher") String teacher,
                                                @RequestParam("students") List<String> students) {
        try {
            PaperBean paperBean = paperService.generatePapers(testId, teacher, students);
            return new PaperReBean(0, paperBean);
        } catch (PaperFullException e) {
            return new PaperReBean(0x300, null);
        } catch (TestEndException e) {
            return new PaperReBean(0x500, null);
        }
    }


}
