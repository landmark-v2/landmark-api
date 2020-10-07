package com.landmark.app.model.dto.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.landmark.app.model.domain.support.Qna;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Data
public class QnaDTO {

    private int id;
    private int userId;
    private String title;
    private String content;
    private Date createdTime;
    private Date modifiedTime;

    private String username;

    public String getCreatedTime() {
        return StaticHelper.dateToString(createdTime, "yyyy-MM-dd HH:mm");
    }

    public String getModifiedTime() {
        return StaticHelper.dateToString(modifiedTime, "yyyy-MM-dd HH:mm");
    }

    public static QnaDTO of(Qna qna) { return MapperUtils.convert(qna, QnaDTO.class); }

    public static List<QnaDTO> of(List<Qna> qna) {
        return MapperUtils.convert(qna, new TypeToken<List<QnaDTO>>(){}.getType());
    }

    public static Page<QnaDTO> of(Page<Qna> qna) {
        return MapperUtils.convert(qna, QnaDTO.class);
    }
}
