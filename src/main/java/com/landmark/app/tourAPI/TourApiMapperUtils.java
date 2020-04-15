package com.landmark.app.tourAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landmark.app.tourAPI.dto.Header;
import com.landmark.app.tourAPI.dto.PageInfo;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedList;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedListResponse;
import com.landmark.app.tourAPI.dto.response.detailIntro.DetailIntro;
import com.landmark.app.tourAPI.dto.response.detailIntro.DetailIntroResponse;
import com.landmark.app.tourAPI.dto.response.searchFestival.SearchFestival;
import com.landmark.app.tourAPI.dto.response.searchFestival.SearchFestivalResponse;
import com.landmark.app.tourAPI.dto.response.searchKeyword.SearchKeyword;
import com.landmark.app.tourAPI.dto.response.searchKeyword.SearchKeywordResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class TourApiMapperUtils {

    private ObjectMapper objectMapper;

    @Autowired
    public TourApiMapperUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // header map to dto
    private Header getHeader(LinkedHashMap<String, Object> header, ObjectMapper objectMapper) {
        Header resp = objectMapper.convertValue(header, Header.class);
        return resp;
    }

    // pageInfo map to dto
    private PageInfo getPageInfo(LinkedHashMap<String, Object> pageInfo, ObjectMapper objectMapper) {
        PageInfo resp = objectMapper.convertValue(pageInfo, PageInfo.class);
        return resp;
    }

    // header, pageInfo, item setting
    private JSONObject getDefaultInfo(LinkedHashMap<String, Object> response, ObjectMapper objectMapper) {
        JSONObject jsonObject = new JSONObject();

        try {
            LinkedHashMap<String, Object> headerMap = (LinkedHashMap) response.get("header");
            Header header = getHeader(headerMap, objectMapper);
            jsonObject.put("header", header);

            LinkedHashMap<String, Object> bodyMap = (LinkedHashMap) response.get("body");

            if (!bodyMap.get("items").getClass().equals(String.class)) {
                LinkedHashMap<String, Object> items = (LinkedHashMap) bodyMap.get("items");

                if (items.get("item").getClass().equals(LinkedHashMap.class)) {
                    LinkedHashMap<String, Object> item = (LinkedHashMap) items.get("item");
                    jsonObject.put("item", item);
                } else {
                    List<LinkedHashMap<String, Object>> item = (ArrayList) items.get("item");
                    jsonObject.put("item", item);
                }
            }

            bodyMap.remove("items");
            PageInfo pageInfo = getPageInfo(bodyMap, objectMapper);
            jsonObject.put("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /** 지역기반 관광정보 조회 응답 */
    public AreaBasedListResponse areaBasedListResponse(JSONObject jsonObject) {
        AreaBasedListResponse areaBasedListResponse = new AreaBasedListResponse();

        try {
            LinkedHashMap<String, Object> response = (LinkedHashMap) jsonObject.get("response");
            JSONObject data = getDefaultInfo(response, objectMapper);

            Header header = (Header) data.get("header");
            areaBasedListResponse.setHeader(header);

            PageInfo pageInfo = (PageInfo) data.get("pageInfo");
            areaBasedListResponse.setPageInfo(pageInfo);

            List<AreaBasedList> areaBasedLists = new ArrayList<>();

            Object item = data.get("item");

            if (item != null) {
                if (item.getClass().equals(LinkedHashMap.class)) {
                    AreaBasedList areaBasedList = objectMapper.convertValue(item, AreaBasedList.class);
                    areaBasedLists.add(areaBasedList);
                } else {
                    for (Object obj : (ArrayList) item) {
                        AreaBasedList areaBasedList = objectMapper.convertValue(obj, AreaBasedList.class);
                        areaBasedLists.add(areaBasedList);
                    }
                }
            }

            areaBasedListResponse.setAreaBasedList(areaBasedLists);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaBasedListResponse;
    }

    /** 키워드 검색 조회 응답 */
    public SearchKeywordResponse searchKeywordResponse(JSONObject jsonObject) {
        SearchKeywordResponse searchKeywordResponse = new SearchKeywordResponse();

        try {
            LinkedHashMap<String, Object> response = (LinkedHashMap) jsonObject.get("response");
            JSONObject data = getDefaultInfo(response, objectMapper);

            Header header = (Header) data.get("header");
            searchKeywordResponse.setHeader(header);

            PageInfo pageInfo = (PageInfo) data.get("pageInfo");
            searchKeywordResponse.setPageInfo(pageInfo);

            List<SearchKeyword> searchKeywords = new ArrayList<>();

            Object item = data.get("item");

            if (item != null) {
                if (item.getClass().equals(LinkedHashMap.class)) {
                    SearchKeyword searchKeyword = objectMapper.convertValue(item, SearchKeyword.class);
                    searchKeywords.add(searchKeyword);
                } else {
                    for (Object obj : (ArrayList) item) {
                        SearchKeyword searchKeyword = objectMapper.convertValue(obj, SearchKeyword.class);
                        searchKeywords.add(searchKeyword);
                    }
                }
            }

            searchKeywordResponse.setSearchKeyword(searchKeywords);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchKeywordResponse;
    }

    /** 행사정보 검색 조회 응답 */
    public SearchFestivalResponse searchFestivalResponse(JSONObject jsonObject) {
        SearchFestivalResponse searchFestivalResponse = new SearchFestivalResponse();

        try {
            LinkedHashMap<String, Object> response = (LinkedHashMap) jsonObject.get("response");
            JSONObject data = getDefaultInfo(response, objectMapper);

            Header header = (Header) data.get("header");
            searchFestivalResponse.setHeader(header);

            PageInfo pageInfo = (PageInfo) data.get("pageInfo");
            searchFestivalResponse.setPageInfo(pageInfo);

            List<SearchFestival> searchFestivals = new ArrayList<>();

            Object item = data.get("item");

            if (item != null) {
                if (item.getClass().equals(LinkedHashMap.class)) {
                    SearchFestival searchFestival = objectMapper.convertValue(item, SearchFestival.class);
                    searchFestivals.add(searchFestival);
                } else {
                    for (Object obj : (ArrayList) item) {
                        SearchFestival searchFestival = objectMapper.convertValue(obj, SearchFestival.class);
                        searchFestivals.add(searchFestival);
                    }
                }
            }

            searchFestivalResponse.setSearchFestival(searchFestivals);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchFestivalResponse;
    }

    /** 소개정보 조회 응답 */
    public DetailIntroResponse detailIntroResponse(JSONObject jsonObject) {
        DetailIntroResponse detailIntroResponse = new DetailIntroResponse();

        try {
            LinkedHashMap<String, Object> response = (LinkedHashMap) jsonObject.get("response");
            JSONObject data = getDefaultInfo(response, objectMapper);

            Header header = (Header) data.get("header");
            detailIntroResponse.setHeader(header);

            PageInfo pageInfo = (PageInfo) data.get("pageInfo");
            detailIntroResponse.setPageInfo(pageInfo);

            List<DetailIntro> detailIntros = new ArrayList<>();

            Object item = data.get("item");

            if (item != null) {
                if (item.getClass().equals(LinkedHashMap.class)) {
                    DetailIntro detailIntro = objectMapper.convertValue(item, DetailIntro.class);
                    detailIntros.add(detailIntro);
                } else {
                    for (Object obj : (ArrayList) item) {
                        DetailIntro detailIntro = objectMapper.convertValue(obj, DetailIntro.class);
                        detailIntros.add(detailIntro);
                    }
                }
            }

            detailIntroResponse.setDetailIntro(detailIntros);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailIntroResponse;
    }

}
