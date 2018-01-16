package edu.nju.test.dao;

import edu.nju.test.domain.Participate;
import edu.nju.test.domain.key.ParticipateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ParticipateDAO extends JpaRepository<Participate, ParticipateKey>{

    Participate findByPassword(String password);

    @Modifying
    @Query(value = "update Participate participate set participate.score=:score where participate.userId=:userId and participate.testId=:testId")
    void updateScore(@Param("score")BigDecimal score, @Param("userId") String userId,@Param("testId")String testId);
}
