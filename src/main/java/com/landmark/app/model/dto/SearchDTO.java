package com.landmark.app.model.dto;

import lombok.Data;

@Data
public class SearchDTO {

    @Data
    public static class SearchKeywordDTO {
        int page;
        int size;
        private String arrange;         // 정렬 구분
        private int contentTypeId;      // 관광타입 id
        private int areaCode;           // 지역코드
        private int sigunguCode;        // 시군구코드
        private String cat1;            // 대분류
        private String cat2;            // 중분류
        private String cat3;            // 소분류
        private String keyword;         // 요청 키워드
    }

    @Data
    public static class AreaBasedListDTO {
        int page;
        int size;
        private String arrange;         // 정렬 구분
        private int contentTypeId;      // 관광타입 id
        private int areaCode;           // 지역코드
        private int sigunguCode;        // 시군구코드
        private String cat1;            // 대분류
        private String cat2;            // 중분류
        private String cat3;            // 소분류
    }

    @Data
    public static class SearchFestivalDTO {
        int page;
        int size;
        private String arrange;         // 정렬 구분
        private int contentTypeId;      // 관광타입 id
        private int areaCode;           // 지역코드
        private int sigunguCode;        // 시군구코드
        private int eventStartDate;     // 행사 시작일
        private int eventEndDate;       // 행사 종료일
    }

    @Data
    public static class DetailIntroDTO {
        int page;
        int size;
        private int contentId;          // 콘텐츠 id
        private int contentTypeId;      // 관광타입 id
    }

}
