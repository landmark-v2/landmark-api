package com.landmark.app.model.repository.support;

import com.landmark.app.model.domain.support.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Integer> {
    @Override
    Optional<QnaComment> findById(Integer integer);

    @Override
    void deleteById(Integer integer);

    @Modifying
    @Transactional
    int deleteByQnaId(int qnaId);

    /*
    @Transactional
    @Modifying
    @Query("update QNA_COMMENT c set " +
            "c.comment=:comment, c.modifiedTime=:modifiedTime " +
            "where c.id=:id and c.userId=:userId and c.qnaId=:qnaId")
    void updateQnaCommentByIdUserId(@Param("comment") String comment, @Param("modifiedTime") Date modifiedTime,
                                    @Param("id") int id, @Param("userId") int userId, @Param("qnaId") int qnaId);

     */
}
