package edu.nju.mailbox.controller;

import edu.nju.mailbox.bean.MailBean;
import edu.nju.mailbox.service.MailboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dongyibo on 2017/11/15.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/mailbox")
public class MailboxController {

    private final MailboxService mailboxService;

    @Autowired
    public MailboxController(MailboxService mailboxService) {
        this.mailboxService = mailboxService;
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public int sendEmail(@RequestParam("email") String email,
                         @RequestParam("content") String content) {
        MailBean mailBean = new MailBean(email, content);
        return this.mailboxService.sendMail(mailBean);
    }


}
