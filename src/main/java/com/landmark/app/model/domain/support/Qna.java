package com.landmark.app.model.domain.support;

import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "QNA")
public class Qna {

    @Id
    private int id;

    @Column(name = "user_id")
    private int userId;

    private String title;

    private String contenet;

    private String comment;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    public static Qna of(QnaDTO qnaDTO) { return MapperUtils.convert(qnaDTO, Qna.class);  }

    public static List<Qna> of(List<QnaDTO> qnaDTOS) {
        return MapperUtils.convert(qnaDTOS, new TypeToken<List<Qna>>(){}.getType());
    }
}
