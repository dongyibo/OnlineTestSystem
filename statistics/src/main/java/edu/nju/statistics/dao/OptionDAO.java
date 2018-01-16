package edu.nju.statistics.dao;

import edu.nju.statistics.domain.Option;
import edu.nju.statistics.domain.key.OptionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionDAO extends JpaRepository<Option, OptionKey> {
}
