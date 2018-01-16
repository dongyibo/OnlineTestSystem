package edu.nju.statistics.dao;

import edu.nju.statistics.domain.TestQue;
import edu.nju.statistics.domain.key.TestQueKey;
import org.aspectj.weaver.ast.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQueDAO extends JpaRepository<TestQue, TestQueKey> {
    /**
     * 获得本次考试所有试题列表
     * @param testId 考试ID
     * @return 试题列表
     */
    @Query("select t from TestQue t join t.paper p where p.testId=:testId")
    List<TestQue> getAllQuestions(@Param("testId") String testId);

    /**
     * 获得某个用户本次考试的题目
     * @param userId 用户ID
     * @param testId 考试ID
     * @return 题目列表
     */
    @Query("select t from TestQue t join t.paper p where p.userId=:userId and p.testId=:testId")
    List<TestQue> getUserQuestions(@Param("userId") String userId, @Param("testId") String testId);
}
