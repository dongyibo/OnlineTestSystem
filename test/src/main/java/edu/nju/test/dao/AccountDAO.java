package edu.nju.test.dao;

import edu.nju.test.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<Account, String> {
    Account findById(String id);
}
