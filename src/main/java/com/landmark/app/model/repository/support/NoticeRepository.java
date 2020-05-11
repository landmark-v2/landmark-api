package com.landmark.app.model.repository.support;

import com.landmark.app.model.domain.support.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    @Override
    void deleteById(Integer integer);
}
