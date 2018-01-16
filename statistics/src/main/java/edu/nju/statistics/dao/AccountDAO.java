package edu.nju.statistics.dao;

import edu.nju.statistics.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<Account, String> {
    Account findById(String id);
}
