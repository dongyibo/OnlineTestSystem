package edu.nju.test.dao;

import edu.nju.test.domain.TestLib;
import edu.nju.test.domain.key.TestLibKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestLibDAO extends JpaRepository<TestLib, TestLibKey> {
    TestLib findByTestIdAndAndQuesId(String testId, String quesId);
}
