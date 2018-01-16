package edu.nju.statistics.dao;

import edu.nju.statistics.domain.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperDAO extends JpaRepository<Paper, String>{
    Paper findById(String id);
}
