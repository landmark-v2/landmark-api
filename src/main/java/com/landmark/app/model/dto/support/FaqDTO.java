package com.landmark.app.model.dto.support;

import com.landmark.app.model.domain.support.Faq;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class FaqDTO {

    private int id;
    private String title;
    private String content;
    private Date createdTime;
    private Date modifiedTime;

    @Getter @Setter
    public static class UpdateFaqDTO {
        private int id;
        private String title;
        private String content;
    }

    public String getCreatedTime() {
        return StaticHelper.dateToString(createdTime, "yyyy-MM-dd HH:mm");
    }

    public String getModifiedTime() {
        return StaticHelper.dateToString(modifiedTime, "yyyy-MM-dd HH:mm");
    }

    public static FaqDTO of(Faq faq) {
        return MapperUtils.convert(faq, FaqDTO.class);
    }

    public static List<FaqDTO> of(List<Faq> Faqs) {
        return MapperUtils.convert(Faqs, new TypeToken<List<FaqDTO>>(){}.getType());
    }

    public static Page<FaqDTO> of(Page<Faq> faq){
        return MapperUtils.convert(faq, FaqDTO.class);
    }
}
