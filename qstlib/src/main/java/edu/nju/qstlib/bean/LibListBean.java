package edu.nju.qstlib.bean;

/**
 * 题库列表中的题库Bean
 */
public class LibListBean {
    private String libId;
    private String libName;

    public LibListBean() {
    }

    public LibListBean(String libId, String libName) {
        this.libId = libId;
        this.libName = libName;
    }

    public String getLibId() {
        return libId;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }
}
