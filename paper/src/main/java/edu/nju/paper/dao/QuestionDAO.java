package edu.nju.paper.dao;

import edu.nju.paper.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDAO extends JpaRepository<Question, String> {
    Question findById(String id);
}
