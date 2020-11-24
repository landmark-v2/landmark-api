package com.landmark.app.model.repository.comment;

import com.landmark.app.model.domain.comment.InfoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfoCommentRepository extends JpaRepository<InfoComment, Integer> {

    InfoComment findById(int id);

    List<InfoComment> findAllByinfoId(int infoId);
}
