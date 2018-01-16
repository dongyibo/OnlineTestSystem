package edu.nju.mailbox.service.impl;

import edu.nju.mailbox.bean.MailBean;
import edu.nju.mailbox.service.MailboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by dongyibo on 2017/11/15.
 */
@Service
public class MailboxServiceImpl implements MailboxService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SUBJECT = "在线考试系统";

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public int sendMail(MailBean mailBean) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mailBean.getEmail());
        message.setSubject(SUBJECT);
        message.setText(mailBean.getContent());

        try {
            sender.send(message);
            logger.info("邮件已发送");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
            return 1;
        }
        return 0;
    }
}
