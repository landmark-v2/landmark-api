package com.landmark.app.model.dto.support;

import com.landmark.app.model.domain.support.Faq;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
public class FaqDTO {

    private int id;
    private String title;
    private String content;
    private Date createdTime;
    private Date modifiedTime;

    public static FaqDTO of(FaqDTO faqDTO) {
        return MapperUtils.convert(faqDTO, FaqDTO.class);
    }

    public static List<TourInfoDTO> of(List<Faq> Faqs) {
        return MapperUtils.convert(Faqs, new TypeToken<List<FaqDTO>>(){}.getType());
    }

    public static Page<FaqDTO> of(Page<Faq> Faqs) {
        return MapperUtils.convert(Faqs, FaqDTO.class);
    }

}
