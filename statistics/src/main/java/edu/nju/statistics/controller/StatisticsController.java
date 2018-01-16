package edu.nju.statistics.controller;

import edu.nju.statistics.bean.*;
import edu.nju.statistics.exception.AuthorityInvalidException;
import edu.nju.statistics.service.PaperService;
import edu.nju.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final PaperService paperService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, PaperService paperService) {
        this.statisticsService = statisticsService;
        this.paperService = paperService;
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.POST)
    public RcodeBean generateStatistics(@PathVariable("testId") String testId){
        RcodeBean bean = new RcodeBean();
        bean.setRcode(statisticsService.generateStatistics(testId));
        return bean;
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public StatisticsReBean checkStatistics(@PathVariable("testId") String testId, @RequestParam("userId") String userId){
        try {
            StatisticsBean statisticsBean = statisticsService.checkStatistics(testId, userId);
            return new StatisticsReBean(0, statisticsBean);
        } catch (AuthorityInvalidException e) {
            StatisticsReBean result = new StatisticsReBean();
            result.setRcode(2);
            return result;
        }
    }

    @RequestMapping(value = "/paper/{testId}", method = RequestMethod.POST)
    public PaperReBean generatePapers(@PathVariable("testId") String testId, @RequestParam("teacher") String teacher,
                                      @RequestParam("students") List<String> students){
        try {
            PaperBean paperBean = paperService.generatePapers(testId, teacher, students);
            return new PaperReBean(0, paperBean);
        } catch (AuthorityInvalidException e) {
            return new PaperReBean(2, null);
        }
    }

}
