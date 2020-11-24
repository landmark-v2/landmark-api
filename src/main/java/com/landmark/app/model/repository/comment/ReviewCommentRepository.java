package com.landmark.app.model.repository.comment;

import com.landmark.app.model.domain.comment.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Integer> {

    List<ReviewComment> findAllByreviewId(int reviewId);

    ReviewComment findById(int id);
}
