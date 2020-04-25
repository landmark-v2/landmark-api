package com.landmark.app.model.domain;

import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 관광지 운영자의 "관광지 정보 등록"
 */
@Entity
@Data
@Table(name = "TOUR_INFO")
public class TourInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;                     // 운영자 인덱스

    private String addr1;                   // 주소

    private String addr2;                   // 상세주소

    @Column(name = "area_code")
    private int areaCode;                   // 지역코드

    @Column(name = "sigungu_code")
    private int sigunguCode;                // 시군구코드

    private String cat1;                    // 대분류

    private String cat2;                    // 중분류

    private String cat3;                    // 소분류

    @Column(name = "content_type_id")
    private int contentTypeId;              // 콘텐츠 타입 id

    @Column(name = "content_id")
    private int contentId;                  // 콘텐츠 id

    private String tel;                     // 전화번호

    private String title;                   // 관광지명

    private String overview;                // 관광지 설명

    @Column(name = "created_time")
    private Date createdTime;               // 등록일자

    @Column(name = "modified_time")
    private Date modifiedTime;              // 수정일자

    @Column(name = "first_image")
    private String firstImage;              // 이미지 파일 경로

    private String homepage;                // url

    public static TourInfo of(TourInfoDTO tourInfoDTO) {
        return MapperUtils.convert(tourInfoDTO, TourInfo.class);
    }

    public static List<TourInfo> of(List<TourInfoDTO> tourInfoDTOS) {
        return MapperUtils.convert(tourInfoDTOS, new TypeToken<List<TourInfo>>(){}.getType());
    }

}
