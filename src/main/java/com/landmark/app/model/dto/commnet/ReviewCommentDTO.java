package com.landmark.app.model.dto.commnet;

import com.landmark.app.model.domain.comment.ReviewComment;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
public class ReviewCommentDTO {
    private int id;
    private int userId;
    private int reviewId;
    private Date createdTime;
    private Date modifiedTime;
    private String comment;

    public static ReviewCommentDTO of(ReviewComment comment) { return MapperUtils.convert(comment, ReviewCommentDTO.class); }

    public static List<ReviewCommentDTO> of(List<ReviewComment> comment) {
        return MapperUtils.convert(comment, new TypeToken<List<ReviewCommentDTO>>(){}.getType());
    }

    public static Page<ReviewCommentDTO> of(Page<ReviewComment> comment) { return MapperUtils.convert(comment, ReviewCommentDTO.class); }

    public String getCreatedTime() {
        return StaticHelper.dateToString(createdTime, "yyyy-MM-dd HH:mm");
    }

    public String getModifiedTime() {
        return StaticHelper.dateToString(modifiedTime, "yyyy-MM-dd HH:mm");
    }
}
