package com.landmark.app.model.domain.comment;

import com.landmark.app.model.dto.support.QnaCommentDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "QNA_COMMENT")
public class QnaComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "qna_id")
    private int qnaId;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    private String comment;

    public static QnaComment of(QnaCommentDTO qnaCommentDTO) { return MapperUtils.convert(qnaCommentDTO, QnaComment.class); }

    public static List<QnaComment> of(List<QnaCommentDTO> qnaCommentDTO){
        return MapperUtils.convert(qnaCommentDTO, new TypeToken<List<QnaComment>>(){}.getType());
    }
}
