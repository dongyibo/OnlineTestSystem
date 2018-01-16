package edu.nju.qstlib.bean;

import java.util.List;

/**
 * 用于题库列表的Bean
 */
public class QstLibBean {
    private int rcode;
    private List<LibListBean> libs;

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public List<LibListBean> getLibs() {
        return libs;
    }

    public void setLibs(List<LibListBean> libs) {
        this.libs = libs;
    }
}
