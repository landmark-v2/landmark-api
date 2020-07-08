package com.landmark.app.model.domain.comment;

import com.landmark.app.model.dto.commnet.ReviewCommentDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "REVIEW_COMMENT")
public class ReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "review_id")
    private int reviewId;

    private String comment;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    public static ReviewComment of(ReviewCommentDTO dto) { return MapperUtils.convert(dto, ReviewComment.class); }

    public static List<ReviewComment> of(List<ReviewCommentDTO> dtos) {
        return MapperUtils.convert(dtos, new TypeToken<List<ReviewComment>>(){}.getType());
    }
}
