package edu.nju.paper.dao;

import edu.nju.paper.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDAO extends JpaRepository<Test, String>{
    Test findById(String id);
}
