package com.landmark.app.model.dto;

import com.landmark.app.model.domain.TourReview;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import java.util.Date;
import java.util.List;

@Data
public class TourReviewDTO {

    private int id;
    private int areaCode;                   // 지역코드
    private int sigunguCode;                // 시군구코드
    private String title;                   // 관광지명 (예 : 별내 스터디 카페)
    private String overView;                // 후기 내용
    private int userId;                     // 글쓴이(사용자) 인덱스
    private int tourId;                     // 관광지 인덱스(TOUR_INFO) -> 관광지 추천 API 호출한 관광지이면 0 으로 등록
    private Date createdTime;               // 등록일자
    private Date modifiedTime;              // 수정일자
    private String firstImage;              // 이미지 파일 경로
    private boolean isPrivate;              // T-비공개, F-공개
    private boolean usedTourApi;            // 관광지 추천 API 사용 여부

    public static TourReviewDTO of(TourReview tourReview) {
        return MapperUtils.convert(tourReview, TourReviewDTO.class);
    }

    public static List<TourReviewDTO> of(List<TourReview> tourReviews) {
        return MapperUtils.convert(tourReviews, new TypeToken<List<TourReviewDTO>>(){}.getType());
    }

    @Data
    public static class RecentReview {
        private String areaName;
        private String sigunguName;
        private String firstImage;
    }

}
