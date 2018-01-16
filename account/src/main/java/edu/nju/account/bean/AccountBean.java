package edu.nju.account.bean;

/**
 * 账户信息Bean
 */
public class AccountBean {
    private String id;
    private String username;
    private String email;
    private int type;

    public AccountBean() {
    }

    /**
     * 账户信息构造函数
     * @param id 用户ID
     * @param username 用户名
     * @param email 用户邮箱
     * @param type 类型
     */
    public AccountBean(String id, String username, String email, int type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.type = type;
    }




    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
