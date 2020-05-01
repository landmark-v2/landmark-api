package com.landmark.app.model.domain.support;

import com.landmark.app.model.dto.support.NoticeDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "NOTICE")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    public static Notice of(NoticeDTO noticeDTO) {
        return MapperUtils.convert(noticeDTO, Notice.class);
    }

    public static List<Notice> of(List<NoticeDTO> noticeDTOS) {
        return MapperUtils.convert(noticeDTOS, new TypeToken<List<Notice>>(){}.getType());
    }
}
