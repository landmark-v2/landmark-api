package com.landmark.app.tourAPI.dto.response.areaBasedList;

import com.landmark.app.utils.helper.StaticHelper;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class AreaBasedList {

    private String addr1;               // 주소
    private String addr2;               // 상세주소
    private int areaCode;               // 지역코드
    private int bookTour;               // 교과서 속 여행지 여부
    private String cat1;                // 대분류
    private String cat2;                // 중분류
    private String cat3;                // 소분류
    private int contentId;              // 콘텐츠 id
    private int contentTypeId;          // 콘텐츠타입 id
    private BigInteger createdTime;     // 등록일
    private String firstImage;          // 대표이미지 (원본)
    private String firstImage2;         // 대표이미지 (썸네일)
    private double mapX;                // X좌표
    private double mapY;                // Y좌표
    private int mLevel;                 // map level
    private BigInteger modifiedTime;    // 수정일
    private int readCount;              // 조회수
    private int sigunguCode;            // 시군구코드
    private String title;               // 제목
    private String zipCode;             // 우편번호

    public Date getCreatedTime() {
        return createdTime != null ? StaticHelper.intToDate(createdTime, "yyyyMMddHHmmss") : null;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = StaticHelper.dateToBigInteger(createdTime, "yyyyMMddHHmmss");
    }

    public Date getModifiedTime() {
        return modifiedTime != null ? StaticHelper.intToDate(modifiedTime, "yyyyMMddHHmmss") : null;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = StaticHelper.dateToBigInteger(modifiedTime, "yyyyMMddHHmmss");
    }

}
