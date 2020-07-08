package com.landmark.app.model.dto.commnet;

import com.landmark.app.model.domain.comment.QnaComment;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
public class QnaCommentDTO {

    private int id;
    private int userId;
    private int qnaId;
    private Date createdTime;
    private Date modifiedTime;
    private String comment;

    public static QnaCommentDTO of(QnaComment qnaComment) { return MapperUtils.convert(qnaComment, QnaCommentDTO.class); }

    public static List<QnaCommentDTO> of(List<QnaComment> qnaComment) {
        return MapperUtils.convert(qnaComment, new TypeToken<List<QnaCommentDTO>>(){}.getType());
    }

    public static Page<QnaCommentDTO> of(Page<QnaComment> qnaComments) { return MapperUtils.convert(qnaComments, QnaCommentDTO.class); }

    public String getCreatedTime() {
        return StaticHelper.dateToString(createdTime, "yyyy-MM-dd HH:mm");
    }

    public String getModifiedTime() {
        return StaticHelper.dateToString(modifiedTime, "yyyy-MM-dd HH:mm");
    }
}
