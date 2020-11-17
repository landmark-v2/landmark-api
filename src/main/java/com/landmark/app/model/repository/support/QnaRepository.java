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

    Qna findById(int id);

    List<Qna> findAll();

    // Qna Search
    List<Qna> findByTitleContaining(String title);
    List<Qna> findByContentContaining(String content);
    List<Qna> findAllByUserId(int userId);

    void deleteById(int id);
}
