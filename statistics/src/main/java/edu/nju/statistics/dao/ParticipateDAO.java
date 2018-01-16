package edu.nju.statistics.dao;

import edu.nju.statistics.domain.Participate;
import edu.nju.statistics.domain.key.ParticipateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ParticipateDAO extends JpaRepository<Participate, ParticipateKey> {

    List<Participate> findByTestId(String testId);

    Participate findByUserIdAndTestId(String userId, String testId);
}
