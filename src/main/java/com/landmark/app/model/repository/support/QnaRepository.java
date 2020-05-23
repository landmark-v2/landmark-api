package com.landmark.app.model.repository.support;

import com.landmark.app.model.domain.support.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, Integer> {

    /*
    @Transactional
    @Modifying
    @Query("update QNA q set q.title=:title, q.content=:content, q.modifiedTime=:modifiedTime where q.id=:id")
    void updateQnaByIdUserId(@Param("title") String title, @Param("content") String content, @Param("modifiedTime") Date modifiedTime, @Param("id") int id);


     */

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Qna> findById(Integer integer);

    List<Qna> findAllByUserId(int userId);

}
