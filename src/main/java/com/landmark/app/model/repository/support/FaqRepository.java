package com.landmark.app.model.repository.support;

import com.landmark.app.model.domain.support.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Integer> {

    @Transactional
    @Modifying
    @Query("update Faq f set " +
            "f.title=:title, f.content=:content, f.modifiedTime=:modifiedTime " +
            "where f.id=:id")
    void updateFaqById(@Param("title") String title, @Param("content") String content, @Param("modifiedTime") Date modifiedTime, @Param("id") int id);

    @Modifying
    @Transactional
    void deleteById(int id);
}
