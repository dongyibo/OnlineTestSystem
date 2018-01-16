package edu.nju.mailbox.service;

import edu.nju.mailbox.bean.MailBean;

/**
 * Created by dongyibo on 2017/11/15.
 */
public interface MailboxService {

    /**
     * 向特定邮箱发送指定内容
     * @param mailBean
     * @return
     */
    public int sendMail(MailBean mailBean);
}
