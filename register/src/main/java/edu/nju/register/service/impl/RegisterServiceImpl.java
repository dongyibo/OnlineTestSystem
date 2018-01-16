package edu.nju.register.service.impl;

import edu.nju.register.bean.AccountBean;
import edu.nju.register.bean.RegisterBean;
import edu.nju.register.dao.RegisterDao;
import edu.nju.register.domain.Account;
import edu.nju.register.exception.PasswordWrongException;
import edu.nju.register.service.RegisterService;
import edu.nju.register.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongyibo on 2017/11/13.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private final RegisterDao registerDao;

    private RestTemplate restTemplate;

    @Autowired
    public RegisterServiceImpl(RegisterDao registerDao, RestTemplate restTemplate) {
        this.registerDao = registerDao;
        this.restTemplate = restTemplate;
    }

    @Override
    public int postInfo(RegisterBean registerBean, String realVerifyCode) {
        Account account = new Account();
        try {
            //加密存储
            account.setPassword(Md5.EncoderByMd5(registerBean.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        account.setUsername(registerBean.getUsername());
        account.setEmail(registerBean.getEmail());
        account.setType(registerBean.getType());

        //获取用户输入验证码
        String verifyCode = registerBean.getVerifyCode();
        //如果 验证码 输入错误
        if (!verifyCode.equals(realVerifyCode)) {
            return 1;
        }
        //判断用户名是否与已有的重复
        ArrayList<String> usernames = this.registerDao.getUsernames();
        if (usernames.contains(account.getUsername())){
            return 2;
        }
        //保存新用户
        List<Account> accounts = this.registerDao.findAll();
        int max = 0;
        if (accounts.size() != 0) {
            for (Account a : accounts) {
                int id = Integer.parseInt(a.getId());
                if (id > max) {
                    max = id;
                }
            }
            max++;
        }

        account.setId(String.format("%05d", max));
        //保存
        this.registerDao.save(account);
        return 0;
    }

    @Override
    public int getVerifyCode(String email, String realVerifyCode) {
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("email", email);
        requestEntity.add("content", realVerifyCode);
        String result = this.restTemplate.postForObject("http://47.100.125.54:8099/mailbox/send", requestEntity, String.class);
        return Integer.parseInt(result);
    }

    @Override
    public AccountBean login(String username, String password) throws PasswordWrongException {
        Account account = registerDao.findAccountByUsername(username);
        if (account == null) {
            throw new PasswordWrongException();
        }
        try {
            if (account.getPassword().equals(Md5.EncoderByMd5(password))) {
                AccountBean bean = new AccountBean();
                bean.setUsername(account.getUsername());
                bean.setEmail(account.getEmail());
                bean.setId(account.getId());
                bean.setType(account.getType());
                return bean;
            } else {
                throw new PasswordWrongException();
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
