package com.landmark.app.model.domain.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.landmark.app.model.domain.user.Role;
import com.landmark.app.model.domain.user.User;
import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import lombok.ToString;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "QNA")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    private String title;

    private String content;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    /*
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
     */

    public static Qna of(QnaDTO qnaDTO) { return MapperUtils.convert(qnaDTO, Qna.class);  }

    public static List<Qna> of(List<QnaDTO> qnaDTOS) {
        return MapperUtils.convert(qnaDTOS, new TypeToken<List<Qna>>(){}.getType());
    }
}
