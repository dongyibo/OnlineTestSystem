package edu.nju.register.dao;

import edu.nju.register.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by dongyibo on 2017/11/15.
 */
@Repository
public interface RegisterDao extends JpaRepository<Account, String> {

    Account findAccountById(String id);

    Account findAccountByUsername(String username);

    /**
     * 获取所有用户名
     * @return
     */
    @Query("select a.username from Account a")
    ArrayList<String> getUsernames();
}
