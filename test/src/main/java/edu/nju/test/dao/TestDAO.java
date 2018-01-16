package edu.nju.test.dao;

import edu.nju.test.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface TestDAO extends JpaRepository<Test, String>{
    List<Test> findByCreator(String creator);

    List<Test> findByStartTimeBetween(Calendar start, Calendar end);

    Test findById(String id);
}
