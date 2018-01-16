package edu.nju.statistics.bean;

public class StatisticsReBean {
    private int rcode;
    private StatisticsBean statistics;

    public StatisticsReBean() {
    }

    public StatisticsReBean(int rcode, StatisticsBean statistics) {
        this.rcode = rcode;
        this.statistics = statistics;
    }

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public StatisticsBean getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsBean statistics) {
        this.statistics = statistics;
    }
}
