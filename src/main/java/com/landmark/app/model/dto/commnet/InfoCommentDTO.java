package com.landmark.app.model.dto.commnet;

import com.landmark.app.model.domain.comment.InfoComment;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
public class InfoCommentDTO {
    private int id;
    private int userId;
    private int infoId;
    private Date createdTime;
    private Date modifiedTime;
    private String comment;

    public static InfoCommentDTO of(InfoComment comment) { return MapperUtils.convert(comment, InfoCommentDTO.class); }

    public static List<InfoCommentDTO> of(List<InfoComment> comment) {
        return MapperUtils.convert(comment, new TypeToken<List<InfoCommentDTO>>(){}.getType());
    }

    public static Page<InfoCommentDTO> of(Page<InfoComment> comment) { return MapperUtils.convert(comment, InfoCommentDTO.class); }

    public String getCreatedTime() {
        return StaticHelper.dateToString(createdTime, "yyyy-MM-dd HH:mm");
    }

    public String getModifiedTime() {
        return StaticHelper.dateToString(modifiedTime, "yyyy-MM-dd HH:mm");
    }
}
