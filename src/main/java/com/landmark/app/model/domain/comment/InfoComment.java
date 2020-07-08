package com.landmark.app.model.domain.comment;

import com.landmark.app.model.dto.commnet.InfoCommentDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "INFO_COMMENT")
public class InfoComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "info_id")
    private int infoId;

    private String comment;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    public static InfoComment of(InfoCommentDTO dto) { return MapperUtils.convert(dto, InfoComment.class); }

    public static List<ReviewComment> of(List<InfoCommentDTO> dtos) {
        return MapperUtils.convert(dtos, new TypeToken<List<InfoComment>>(){}.getType());
    }
}
