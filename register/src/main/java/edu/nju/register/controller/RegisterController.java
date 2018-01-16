package edu.nju.register.controller;

import edu.nju.register.bean.AccountBean;
import edu.nju.register.bean.AccountReBean;
import edu.nju.register.bean.RegisterBean;
import edu.nju.register.exception.PasswordWrongException;
import edu.nju.register.service.RegisterService;
import edu.nju.register.util.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public int postInfo(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("email") String email,
                        @RequestParam("type") int type,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        RegisterBean registerBean = new RegisterBean(username, password, email, type, verifyCode);
        //获取验证码
        String realVerifyCode = (String) session.getAttribute("verifyCode");
        return this.registerService.postInfo(registerBean, realVerifyCode);
    }

    @RequestMapping(value = "/verify-code", method = RequestMethod.GET)
    public int getVerifyCode(@RequestParam("email") String email,
                             HttpSession session) {
        //生成验证码
        String realVerifyCode = VerifyCode.generateVerifyCode();
        session.setAttribute("verifyCode", realVerifyCode);
        return this.registerService.getVerifyCode(email, realVerifyCode);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AccountReBean login(@RequestParam("username") String username, @RequestParam("password") String password){
        AccountBean account = null;
        try {
            account = registerService.login(username, password);
        } catch (PasswordWrongException e) {
            return new AccountReBean(5, null);
        }
        return new AccountReBean(0, account);
    }

}
