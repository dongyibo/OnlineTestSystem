package edu.nju.statistics.dao;

import edu.nju.statistics.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDAO extends JpaRepository<Test, String>{
    Test findById(String id);
}
