package com.landmark.app.model.dto;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Data
public class TourInfoDTO {

    private int id;
    private int userId;                     // 운영자 인덱스
    private String addr1;                   // 주소
    private String addr2;                   // 상세주소
    private int areaCode;                   // 지역코드
    private int sigunguCode;                // 시군구코드
    private String cat1;                    // 대분류
    private String cat2;                    // 중분류
    private String cat3;                    // 소분류
    private int contentId;                  // 콘텐츠 id
    private int contentTypeId;              // 콘텐츠 타입 id
    private String tel;                     // 전화번호
    private String title;                   // 관광지명
    private String overview;                // 관광지 설명
    private Date createdTime;               // 등록일자
    private Date modifiedTime;              // 수정일자
    private String firstImage;              // 이미지 파일 경로
    private String firstImage2;             // 썸네일 이미지 파일 경로
    private String homepage;                // url
    private int readCount;                  // 조회수 : redis 에 저장 (key : TourInfo{id}, value : count)

    public String getCreatedTime() {
        return StaticHelper.dateToString(createdTime, "yyyy-MM-dd HH:mm");
    }

    public String getModifiedTime() {
        return StaticHelper.dateToString(modifiedTime, "yyyy-MM-dd HH:mm");
    }

    public static TourInfoDTO of(TourInfo tourInfo) {
        return MapperUtils.convert(tourInfo, TourInfoDTO.class);
    }

    public static List<TourInfoDTO> of(List<TourInfo> tourInfos) {
        return MapperUtils.convert(tourInfos, new TypeToken<List<TourInfoDTO>>(){}.getType());
    }

    public static Page<TourInfoDTO> of(Page<TourInfo> tourInfos) {
        return MapperUtils.convert(tourInfos, TourInfoDTO.class);
    }


    @Data
    public static class RegisteredTourInfoDTO {
        private int id;
        private String title;
    }

    @Data
    public static class DeleteTourInfoDTO{
        private int id;
        private int user_id;
    }

}
