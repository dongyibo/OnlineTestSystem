package edu.nju.mailbox.bean;

/**
 * Created by dongyibo on 2017/11/15.
 */
public class MailBean {

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 发送内容
     */
    private String content;

    public MailBean() {
    }

    public MailBean(String email, String content) {
        this.email = email;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
