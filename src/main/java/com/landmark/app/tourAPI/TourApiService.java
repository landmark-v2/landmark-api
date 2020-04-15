package com.landmark.app.tourAPI;

import com.landmark.app.tourAPI.dto.request.DefaultInfo;
import com.landmark.app.tourAPI.dto.request.TourApiRequest;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedListResponse;
import com.landmark.app.tourAPI.dto.response.detailIntro.DetailIntroResponse;
import com.landmark.app.tourAPI.dto.response.searchFestival.SearchFestivalResponse;
import com.landmark.app.tourAPI.dto.response.searchKeyword.SearchKeywordResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.landmark.app.utils.Constants.*;

@Service
public class TourApiService {

    private RestTemplate restTemplate;
    private TourApiMapperUtils tourApiMapperUtils;

    @Autowired
    public TourApiService(RestTemplate restTemplate, TourApiMapperUtils tourApiMapperUtils) {
        this.restTemplate = restTemplate;
        this.tourApiMapperUtils = tourApiMapperUtils;
    }

    // tourAPI 호출
    private JSONObject response(String url) {
        try {
            return restTemplate.getForObject(URI.create(url), JSONObject.class);
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", url);
            jsonObject.put("errMsg", e.getMessage());
            return jsonObject;
        }
    }

    // default 정보 세팅
    private DefaultInfo getDefaultInfo(int page, int size) {
        DefaultInfo defaultInfo = new DefaultInfo();
        defaultInfo.setPageNo(page);
        defaultInfo.setNumOfRows(size);
        return defaultInfo;
    }

    // 호출할 url 생성
    private String getUrl(String api, TourApiRequest tourApiRequest) {
        String url = api + tourApiRequest.toString();
        return url;
    }

    /**
     * 지역기반 관광정보 조회
     */
    public AreaBasedListResponse areaBasedList(int page, int size, TourApiRequest tourApiRequest) {
        tourApiRequest.setDefaultInfo(getDefaultInfo(page, size));
        JSONObject jsonObject = response(getUrl(AREA_BASED_LIST_API, tourApiRequest));
        return tourApiMapperUtils.areaBasedListResponse(jsonObject);
    }

    /**
     * 키워드 검색 조회
     */
    public SearchKeywordResponse searchKeyword(int page, int size, TourApiRequest tourApiRequest) {
        tourApiRequest.setDefaultInfo(getDefaultInfo(page, size));
        JSONObject jsonObject = response(getUrl(SEARCH_KEYWORD_API, tourApiRequest));
        return tourApiMapperUtils.searchKeywordResponse(jsonObject);
    }

    /**
     * 행사정보 조회
     */
    public SearchFestivalResponse searchFestival(int page, int size, TourApiRequest tourApiRequest) {
        tourApiRequest.setDefaultInfo(getDefaultInfo(page, size));
        JSONObject jsonObject = response(getUrl(SEARCH_FESTIVAL_API, tourApiRequest));
        return tourApiMapperUtils.searchFestivalResponse(jsonObject);
    }

    /**
     * 소개정보 조회
     */
    public DetailIntroResponse detailIntro(int page, int size, TourApiRequest tourApiRequest) {
        tourApiRequest.setDefaultInfo(getDefaultInfo(page, size));
        JSONObject jsonObject = response(getUrl(DETAIL_INTRO_API, tourApiRequest));
        return tourApiMapperUtils.detailIntroResponse(jsonObject);
    }

}
