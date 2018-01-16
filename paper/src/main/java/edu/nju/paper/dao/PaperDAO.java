package edu.nju.paper.dao;

import edu.nju.paper.domain.Paper;
import edu.nju.paper.domain.Participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dongyibo on 2017/11/27.
 */
@Repository
public interface PaperDAO extends JpaRepository<Paper, String> {

    List<Paper> findByTestId(String testId);

    Paper findByTestIdAndUserId(String testId, String userId);
}
