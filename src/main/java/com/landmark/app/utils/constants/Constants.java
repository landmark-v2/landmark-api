package com.landmark.app.utils.constants;

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
    public static final String DETAIL_COMMON_API     = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?";

    /** API **/
    public static final String SEARCH_API           = "/search";
    public static final String TOUR_REVIEW_API      = "/review";
    public static final String FILE_API             = "/file";
    public static final String TOUR_INQUIRY_API            = "/inquiry";

    /** ROLE **/
    public static final String ROLE_USER        = "ROLE_USER";      // 일반 사용자
    public static final String ROLE_ADMIN       = "ROLE_ADMIN";     // 관광지 관리자
    public static final String ROLE_DEV         = "ROLE_DEV";       // 개발자

    /** Search Review Type **/
    public static final int REVIEW_TYPE_ALL     = 0;    // 전체 조회
    public static final int REVIEW_TYPE_ACCOUNT = 1;    // 사용자 아이디로 조회
    public static final int REVIEW_TYPE_TITLE   = 2;    // 관광지명으로 조회

    /** Content Type Id **/
    public static final int CONTENT_TYPE_ID_TOUR            = 12;   // 관광지
    public static final int CONTENT_TYPE_ID_CULTURE         = 14;   // 문화시설
    public static final int CONTENT_TYPE_ID_FESTIVAL        = 15;   // 행사/공연/축제
    public static final int CONTENT_TYPE_ID_TRAVEL_COURSE   = 25;   // 여행코스
    public static final int CONTENT_TYPE_ID_LEPORTS         = 28;   // 레포츠
    public static final int CONTENT_TYPE_ID_SHOPPING        = 38;   // 쇼핑
    public static final int CONTENT_TYPE_ID_FOOD            = 39;   // 음식점

}
