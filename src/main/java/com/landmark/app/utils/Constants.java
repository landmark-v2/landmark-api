package com.landmark.app.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static Map<Integer, String> areaCodeMap = new HashMap<>();
    public static Map<Map<Integer, Integer>, String> sigunguCodeMap = new HashMap<>();
    public static Map<Integer, String> contentTypeMap = new HashMap<>();
    public static Map<String, String> cat1Map = new HashMap<>();
    public static Map<String, String> cat2Map = new HashMap<>();
    public static Map<String, String> cat3Map = new HashMap<>();

    /** 국내 관광지 추천 API **/
    public static final String AREA_BASED_LIST_API  = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?";
    public static final String SEARCH_KEYWORD_API   = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?";
    public static final String SEARCH_FESTIVAL_API  = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?";
    public static final String DETAIL_INTRO_API     = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?";

    /** API **/
    public static final String SEARCH_API           = "/search";
    public static final String TOUR_REVIEW_API      = "/review";
    public static final String FILE_API             = "/file";

    /** ROLE **/
    public static final String ROLE_USER        = "ROLE_USER";      // 일반 사용자
    public static final String ROLE_ADMIN       = "ROLE_ADMIN";     // 관광지 관리자
    public static final String ROLE_DEV         = "ROLE_DEV";       // 개발자

    /** Search Review Type **/
    public static final int REVIEW_TYPE_ALL     = 0;    // 전체 조회
    public static final int REVIEW_TYPE_ACCOUNT = 1;    // 사용자 아이디로 조회
    public static final int REVIEW_TYPE_TITLE   = 2;    // 관광지명으로 조회

}
