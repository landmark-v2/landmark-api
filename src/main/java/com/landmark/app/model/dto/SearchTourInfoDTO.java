package com.landmark.app.model.dto;

import lombok.Data;

@Data
public class SearchTourInfoDTO {

    private int page;
    private int size;
    private int type;       // 정렬 기준 : 1. 등록일순, 2. 제목순
    private int sort;       // 정렬 : 1. 내림차순, 2. 오름차순
    private int areaCode;
    private int sigunguCode;
    /**
     * [contentTypeId]
     * 12 : 관광지, 14 : 문화시설, 15 : 행사/공연/축제,
     * 25 : 여행코스, 28 : 레포츠, 38 : 쇼핑, 39 : 음식점
     */
    private int contentTypeId;
    private String cat1;
    private String cat2;
    private String cat3;
    private String keyword;

}
