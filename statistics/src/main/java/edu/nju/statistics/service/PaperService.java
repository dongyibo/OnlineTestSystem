package edu.nju.statistics.service;

import edu.nju.statistics.bean.PaperBean;
import edu.nju.statistics.exception.AuthorityInvalidException;

import java.util.List;

public interface PaperService {
    PaperBean generatePapers(String testId, String teacher, List<String> students) throws AuthorityInvalidException;
}
