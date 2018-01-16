package edu.nju.statistics.service;

import edu.nju.statistics.bean.StatisticsBean;
import edu.nju.statistics.exception.AuthorityInvalidException;

public interface StatisticsService {
    int generateStatistics(String testId);

    StatisticsBean checkStatistics(String testId, String userId) throws AuthorityInvalidException;
}
