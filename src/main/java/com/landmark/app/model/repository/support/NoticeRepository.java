package com.landmark.app.model.repository.support;

import com.landmark.app.model.domain.support.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    @Transactional
    @Modifying
    @Query("update Notice n set " +
            "n.title =: title, n.content =: content, n.modifiedTime =: modifiedTime " +
            "where n.id =: id")
    void updateNoticeById(@Param("title") String title, @Param("content") String content, @Param("modifiedTime") Date modifiedTime, @Param("id") int id);

    @Query("delete from Notice n where n.id =: id")
    void deleteNoticeById(@Param("id") int id);
}
