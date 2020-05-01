package com.landmark.app.model.dto;

import com.landmark.app.model.domain.TourReview;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class TourReviewDTO {

    private int id;
    @Min(0)
    private int areaCode;                   // 지역코드
    private int sigunguCode;                // 시군구코드
    @NotNull
    private String title;                   // 관광지명 (예 : 별내 스터디 카페)
    private String overview;                // 후기 내용
    private int userId;                     // 글쓴이(사용자) 인덱스
    private int tourId;                     // 관광지 인덱스(TOUR_INFO) -> 관광지 추천 API 호출한 관광지이면 0 으로 등록
    private Date createdTime;               // 등록일자
    private Date modifiedTime;              // 수정일자
    private String firstImage;              // 이미지 파일 경로
    private boolean isPrivate;              // T-비공개, F-공개

    public static TourReviewDTO of(TourReview tourReview) {
        return MapperUtils.convert(tourReview, TourReviewDTO.class);
    }

    public static List<TourReviewDTO> of(List<TourReview> tourReviews) {
        return MapperUtils.convert(tourReviews, new TypeToken<List<TourReviewDTO>>(){}.getType());
    }

    public static Page<TourReviewDTO> of(Page<TourReview> tourReviews) {
        return MapperUtils.convert(tourReviews, TourReviewDTO.class);
    }

    @Data
    public static class RecentReview {
        private String areaName;
        private String sigunguName;
        private String firstImage;
    }

    @Data
    public static class SearchReviewDTO {
        private int page;
        private int size;
        private int type;       // 0. 전체, 1. 사용자 아이디, 2. 관광지명
        private int tourId;
        private String q;       // 검색어
        private String startDate;
        private String endDate;

        public Date getStartDate() {
            return StaticHelper.stringToDate(startDate, "yyyyMMdd");
        }

        public Date getEndDate() {
            return StaticHelper.stringToDate(endDate, "yyyyMMdd");
        }
    }

    @Data
    public static class FileDTO {
        private int reviewId;
        private String path;
    }

}
