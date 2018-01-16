package edu.nju.testman.dao;

import edu.nju.testman.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<Account, String>{
    Account findByEmail(String email);

    Account findById(String id);
}
