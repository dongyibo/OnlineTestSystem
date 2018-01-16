package edu.nju.account.controller;

import edu.nju.account.bean.AccountBean;
import edu.nju.account.bean.AccountListBean;
import edu.nju.account.bean.AccountReBean;
import edu.nju.account.exception.AuthorityInvalidException;
import edu.nju.account.exception.PasswordWrongException;
import edu.nju.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public AccountListBean accountList(@RequestParam("userId") String userId){
        AccountListBean result = new AccountListBean();
        try {
            result.setList(accountService.getAccountList(userId));
            result.setRcode(0);
        } catch (AuthorityInvalidException e) {
            result.setRcode(2);
        }
        return result;
    }

    @RequestMapping(value = "/user/{accountId}", method = RequestMethod.DELETE)
    public AccountListBean deleteAccount(@RequestBody String userId,
                                         @PathVariable("accountId") String accountId){
        AccountListBean result = new AccountListBean();
        for(int i=0;i<userId.length();i++){
            if(userId.charAt(i)=='='){
                userId=userId.substring(i+1,userId.length());
                break;
            }
        }
        try {
            result.setList(accountService.deleteAccount(userId, accountId));
            result.setRcode(0);
        } catch (AuthorityInvalidException e) {
            result.setRcode(2);
        }
        return result;
    }

    @RequestMapping(value = "/user/{accountId}", method = RequestMethod.PUT)
    public AccountReBean changeAccount(@PathVariable("accountId") String accountId, @RequestParam("username")
                                       String username,@RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("password") String password){
        try {
            AccountBean account = accountService.changeAccount(accountId, username, oldPassword, password);
            return new AccountReBean(0, account);
        } catch (PasswordWrongException e) {
            return new AccountReBean(5, null);
        }
    }
}
