package edu.nju.account.service.impl;

import edu.nju.account.bean.AccountBean;
import edu.nju.account.dao.AccountDAO;
import edu.nju.account.domain.Account;
import edu.nju.account.exception.AuthorityInvalidException;
import edu.nju.account.exception.PasswordWrongException;
import edu.nju.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public List<AccountBean> getAccountList(String userId) throws AuthorityInvalidException {
        Account user = accountDAO.findById(userId);
        if (user.getType() == 0){ // 是管理员
            List<Account> accounts = accountDAO.findAll();
            List<AccountBean> result = new ArrayList<>();
            for (Account a:accounts){
                AccountBean bean = new AccountBean(a.getId(), a.getUsername(), a.getEmail(), a.getType());
                result.add(bean);
            }
            return result;
        } else { // 否则抛出异常
            throw new AuthorityInvalidException();
        }
    }

    @Override
    public List<AccountBean> deleteAccount(String userId, String accountId) throws AuthorityInvalidException {
        Account user = accountDAO.findById(userId);
        if (user.getType() == 0){ // 是管理员
            accountDAO.delete(accountId);
            List<Account> accounts = accountDAO.findAll();
            List<AccountBean> result = new ArrayList<>();
            for (Account a:accounts){
                AccountBean bean = new AccountBean(a.getId(), a.getUsername(), a.getEmail(), a.getType());
                result.add(bean);
            }
            return result;
        } else {
            throw new AuthorityInvalidException();
        }
    }

    @Override
    public AccountBean changeAccount(String accountId, String username, String oldPassword, String password) throws PasswordWrongException {
        Account user = accountDAO.findById(accountId);
        if (user.getPassword().equals(oldPassword)){
            user.setUsername(username);
            user.setPassword(password);
            accountDAO.save(user);
            user = accountDAO.findById(accountId);
            AccountBean accountBean = new AccountBean();
            accountBean.setEmail(user.getEmail());
            accountBean.setId(user.getId());
            accountBean.setType(user.getType());
            accountBean.setUsername(user.getUsername());
            return accountBean;
        } else {
            throw new PasswordWrongException();
        }

    }


}
