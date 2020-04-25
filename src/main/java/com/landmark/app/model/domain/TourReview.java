package com.landmark.app.model.domain;

import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 일반 사용자의 "여행 후기"
 */
@Entity
@Data
@Table(name = "TOUR_REVIEW")
public class TourReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "area_code")
    private int areaCode;                   // 지역코드

    @Column(name = "sigungu_code")
    private int sigunguCode;                // 시군구코드

    private String title;                   // 관광지명 (예 : 별내 스터디 카페)

    private String overview;                // 후기 내용

    @Column(name = "user_id")
    private int userId;                     // 글쓴이(사용자) 인덱스

    @Column(name = "tour_id")
    private int tourId;                     // 관광지 인덱스(TOUR_INFO) -> 관광지 추천 API 호출한 관광지이면 0 으로 등록

    @Column(name = "created_time")
    private Date createdTime;               // 등록일자

    @Column(name = "modified_time")
    private Date modifiedTime;              // 수정일자

    @Column(name = "first_image")
    private String firstImage;              // 이미지 파일 경로

    @Column(name = "is_private")
    private boolean isPrivate;              // T-비공개, F-공개

    @Column(name = "used_tour_api")
    private boolean usedTourApi;            // 관광지 추천 API 사용 여부

    public static TourReview of(TourReviewDTO tourReviewDTO) {
        return MapperUtils.convert(tourReviewDTO, TourReview.class);
    }

    public static List<TourReview> of(List<TourReviewDTO> tourReviewDTOS) {
        return MapperUtils.convert(tourReviewDTOS, new TypeToken<List<TourReview>>(){}.getType());
    }

}
