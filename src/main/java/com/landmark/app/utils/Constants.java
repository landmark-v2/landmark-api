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

    /** TOUR API **/
    public static final String AREA_BASED_LIST_API = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?";
    public static final String SEARCH_KEYWORD_API = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?";
    public static final String SEARCH_FESTIVAL_API = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?";
    public static final String DETAIL_INTRO_API = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?";

    /** API **/
    public static final String TOUR_HISTORY_API = "/tour-history";
    public static final String SEARCH_API       = "/search";

}
