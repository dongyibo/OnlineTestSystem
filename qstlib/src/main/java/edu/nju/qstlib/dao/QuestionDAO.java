package edu.nju.qstlib.dao;

import edu.nju.qstlib.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question, String> {
    List<Question> findByLibId(String libId);

    Question findById(String id);
}
