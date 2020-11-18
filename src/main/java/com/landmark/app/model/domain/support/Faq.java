package com.landmark.app.model.domain.support;

import com.landmark.app.model.dto.support.FaqDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "FAQ")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    public static Faq of(FaqDTO faqDTO) {
        return MapperUtils.convert(faqDTO, Faq.class);
    }

    public static List<Faq> of(List<FaqDTO> faqDTOS) {
        return MapperUtils.convert(faqDTOS, new TypeToken<List<Faq>>(){}.getType());
    }

}
