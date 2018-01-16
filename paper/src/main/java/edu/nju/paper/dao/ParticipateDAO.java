package edu.nju.paper.dao;

import edu.nju.paper.domain.Participate;
import edu.nju.paper.domain.key.ParticipateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipateDAO extends JpaRepository<Participate, ParticipateKey> {
    Participate findByUserIdAndTestId(String userId, String testId);
}
