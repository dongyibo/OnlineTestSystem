package edu.nju.account.service;

import edu.nju.account.bean.AccountBean;
import edu.nju.account.exception.AuthorityInvalidException;
import edu.nju.account.exception.PasswordWrongException;

import java.util.List;

public interface AccountService {
    /**
     * 获得账户列表
     * @param userId 用户ID，用于验证身份
     * @return 账户列表
     */
    public List<AccountBean> getAccountList(String userId) throws AuthorityInvalidException;

    /**
     * 删除账户
     * @param userId 管理员ID，用于验证身份
     * @param accountId 要删除的账户ID
     * @return 账户列表
     */
    public List<AccountBean> deleteAccount(String userId, String accountId) throws AuthorityInvalidException;

    /**
     * 修改账户信息
     * @param accountId 账户ID
     * @param username 用户名
     * @param password 使用Base64加密的密码
     * @return 新的账户信息
     */
    public AccountBean changeAccount(String accountId, String username, String oldPassword, String password) throws PasswordWrongException;
}