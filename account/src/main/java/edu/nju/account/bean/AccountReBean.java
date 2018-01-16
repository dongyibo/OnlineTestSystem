package edu.nju.account.bean;

public class AccountReBean {
    private int rcode;
    private AccountBean account;

    public AccountReBean(int rcode, AccountBean account) {
        this.rcode = rcode;
        this.account = account;
    }

    public AccountReBean() {
    }

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }
}
