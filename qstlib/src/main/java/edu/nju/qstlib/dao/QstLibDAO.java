package edu.nju.qstlib.dao;

import edu.nju.qstlib.domain.QstLib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QstLibDAO extends JpaRepository<QstLib, String> {
    List<QstLib> findByUserId(String userId);

    QstLib findById(String id);

    @Query(value = "update QstLib q set q.name=:libName where q.id=:libId")
    void updateLibName(@Param("libId") String libId, @Param("libName") String libName);
}
