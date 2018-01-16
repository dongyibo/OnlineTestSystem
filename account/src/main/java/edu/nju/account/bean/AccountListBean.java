package edu.nju.account.bean;

import java.util.List;

/**
 * 账户列表Bean
 */
public class AccountListBean {
    private int rcode;
    private List<AccountBean> list;

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public List<AccountBean> getList() {
        return list;
    }

    public void setList(List<AccountBean> list) {
        this.list = list;
    }
}
