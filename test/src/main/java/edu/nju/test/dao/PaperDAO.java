package edu.nju.test.dao;

import edu.nju.test.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperDAO extends JpaRepository<Paper, String> {
    Paper findByUserIdAndTestId(String userId, String testId);

    List<Paper> findByTestId(String testId);
}
