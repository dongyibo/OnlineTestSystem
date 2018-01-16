package edu.nju.register.bean;

/**
 * 账户信息Bean
 */
public class RegisterBean {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 类型
     */
    private int type;

    /**
     * 验证码
     */
    private String verifyCode;

    public RegisterBean() {
    }

    public RegisterBean(String username, String password, String email, int type, String verifyCode) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
        this.verifyCode = verifyCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
