package com.landmark.app.tourAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landmark.app.tourAPI.dto.Header;
import com.landmark.app.tourAPI.dto.PageInfo;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedList;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedListResponse;
import com.landmark.app.tourAPI.dto.response.detailCommon.DetailCommon;
import com.landmark.app.tourAPI.dto.response.detailCommon.DetailCommonResponse;
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

            if (bodyMap != null) {
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
            }

            bodyMap.remove("items");

            if (bodyMap != null) {
                PageInfo pageInfo = getPageInfo(bodyMap, objectMapper);
                jsonObject.put("pageInfo", pageInfo);
            } else {
                jsonObject.put("pageInfo", null);
            }
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

    /** 공통정보 조회 응답 */
    public DetailCommonResponse detailCommonResponse(JSONObject jsonObject) {
        DetailCommonResponse detailCommonResponse = new DetailCommonResponse();

        try {
            LinkedHashMap<String, Object> response = (LinkedHashMap) jsonObject.get("response");
            JSONObject data = getDefaultInfo(response, objectMapper);

            Header header = (Header) data.get("header");
            detailCommonResponse.setHeader(header);

            PageInfo pageInfo = (PageInfo) data.get("pageInfo");
            detailCommonResponse.setPageInfo(pageInfo);

            List<DetailCommon> detailCommons = new ArrayList<>();

            Object item = data.get("item");

            if (item != null) {
                if (item.getClass().equals(LinkedHashMap.class)) {
                    DetailCommon detailCommon = objectMapper.convertValue(item, DetailCommon.class);
                    detailCommons.add(detailCommon);
                } else {
                    for (Object obj : (ArrayList) item) {
                        DetailCommon detailCommon = objectMapper.convertValue(obj, DetailCommon.class);
                        detailCommons.add(detailCommon);
                    }
                }
            }

            detailCommonResponse.setDetailCommon(detailCommons);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailCommonResponse;
    }

}
