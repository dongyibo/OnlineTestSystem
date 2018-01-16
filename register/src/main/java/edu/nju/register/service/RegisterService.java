package edu.nju.register.service;

import edu.nju.register.bean.AccountBean;
import edu.nju.register.bean.RegisterBean;
import edu.nju.register.exception.PasswordWrongException;

/**
 * Created by dongyibo on 2017/11/13.
 */
public interface RegisterService {

    /**
     * 用户注册信息提交
     * @param registerBean
     * @param realVerifyCode
     * @return
     */
    public int postInfo(RegisterBean registerBean, String realVerifyCode);

    /**
     * 获取验证码
     * @param email
     * @param realVerifyCode
     * @return
     */
    public int getVerifyCode(String email, String realVerifyCode);


    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 账户信息
     */
    public AccountBean login(String username, String password) throws PasswordWrongException;
}
