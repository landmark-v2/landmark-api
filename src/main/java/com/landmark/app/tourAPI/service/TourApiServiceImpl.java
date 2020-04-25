package com.landmark.app.tourAPI.service;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.service.RedisService;
import com.landmark.app.tourAPI.TourApiMapperUtils;
import com.landmark.app.tourAPI.dto.request.DefaultInfo;
import com.landmark.app.tourAPI.dto.request.TourApiRequest;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedList;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedListResponse;
import com.landmark.app.tourAPI.dto.response.detailCommon.DetailCommon;
import com.landmark.app.tourAPI.dto.response.detailCommon.DetailCommonResponse;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.StaticHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.landmark.app.utils.Constants.*;

@Service
public class TourApiServiceImpl extends LoggerUtils implements TourApiService {

    private RestTemplate restTemplate;
    private TourApiMapperUtils tourApiMapperUtils;
    private TourInfoRepository tourInfoRepository;
    private RedisService redisService;

    @Autowired
    public TourApiServiceImpl(RestTemplate restTemplate, TourApiMapperUtils tourApiMapperUtils, TourInfoRepository tourInfoRepository, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.tourApiMapperUtils = tourApiMapperUtils;
        this.tourInfoRepository = tourInfoRepository;
        this.redisService = redisService;
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

    @Override
    public AreaBasedListResponse areaBasedList(int page, int size, TourApiRequest tourApiRequest) {
        tourApiRequest.setDefaultInfo(getDefaultInfo(page, size));
        JSONObject jsonObject = response(getUrl(AREA_BASED_LIST_API, tourApiRequest));
        return tourApiMapperUtils.areaBasedListResponse(jsonObject);
    }

    @Override
    public DetailCommonResponse detailCommon(TourApiRequest tourApiRequest) {
        tourApiRequest.setDefaultInfo(getDefaultInfo(0, 0));
        JSONObject jsonObject = response(getUrl(DETAIL_COMMON_API, tourApiRequest));
        return tourApiMapperUtils.detailCommonResponse(jsonObject);
    }

    @Override
    public int saveTourInfoFromTourAPI() {
        int saveCount = 0;

        try {
            TourApiRequest tourApiRequest = new TourApiRequest();
            tourApiRequest.setListYN("Y");
            tourApiRequest.setArrange("D"); // 생성일자 순으로 정렬
            AreaBasedListResponse areaBasedListResponse = areaBasedList(1, 1, tourApiRequest);

            // 1. 레디스에 저장된 전날 total count 와 당일의 total count 를 조회 후 차이 나는 개수 만큼 디비에 추가 저장한다.
            int todayTotalCount = areaBasedListResponse.getPageInfo() != null ? areaBasedListResponse.getPageInfo().getTotalCount() : 0;
            int yesterdayTotalCount = getYesterdayTotalCount();
            int addCount = todayTotalCount - yesterdayTotalCount;

            if (addCount > 0) {
                // 레디스에 totalCount 저장
                saveTodayTotalCount(todayTotalCount);

                TourApiRequest todayTourApiRequest = new TourApiRequest();
                todayTourApiRequest.setListYN("Y");
                todayTourApiRequest.setArrange("D"); // 생성일자 순으로 정렬
                AreaBasedListResponse todayAreaBasedListResponse = areaBasedList(1, addCount, todayTourApiRequest);

                List<AreaBasedList> areaBasedList = todayAreaBasedListResponse.getAreaBasedList();

                for (AreaBasedList areaBasedInfo : areaBasedList) {
                    TourInfoDTO tourInfoDTO = new TourInfoDTO();
                    tourInfoDTO.setAddr1(areaBasedInfo.getAddr1());
                    tourInfoDTO.setAddr2(areaBasedInfo.getAddr2());
                    tourInfoDTO.setAreaCode(areaBasedInfo.getAreaCode());
                    tourInfoDTO.setSigunguCode(areaBasedInfo.getSigunguCode());
                    tourInfoDTO.setContentTypeId(areaBasedInfo.getContentTypeId());
                    tourInfoDTO.setContentId(areaBasedInfo.getContentId());
                    tourInfoDTO.setCat1(areaBasedInfo.getCat1());
                    tourInfoDTO.setCat2(areaBasedInfo.getCat2());
                    tourInfoDTO.setCat3(areaBasedInfo.getCat3());
                    tourInfoDTO.setCreatedTime(areaBasedInfo.getCreatedTime());
                    tourInfoDTO.setModifiedTime(areaBasedInfo.getModifiedTime());
                    tourInfoDTO.setTitle(areaBasedInfo.getTitle());
                    tourInfoDTO.setFirstImage(areaBasedInfo.getFirstImage());
                    tourInfoDTO.setFirstImage2(areaBasedInfo.getFirstImage2());
                    tourInfoDTO.setReadCount(areaBasedInfo.getReadCount());
                    // 관광지 API 에서 조회한 정보들의 user idx 는 0 으로 한다.
                    tourInfoDTO.setUserId(0);
                    tourInfoDTO = TourInfoDTO.of(tourInfoRepository.save(TourInfo.of(tourInfoDTO)));
                    // tel, homepage, overview 추가 저장
                    updateTourInfoFromTourAPI(tourInfoDTO);
                    saveCount++;
                }
            }
        } catch (Exception e) {
            logger.error("saveTourInfoFromTourAPI : " + e.getMessage());
            saveCount = 0;
        }

        if (saveCount > 0) {
            // 2. 해당 일자의 total count 를 레디스에 저장한다.
            saveTodayTotalCount(saveCount);
            logger.info("saveTourInfoFromTourAPI count = " + saveCount);
        }

        return saveCount;
    }

    private void updateTourInfoFromTourAPI(TourInfoDTO tourInfoDTO) {
        try {
            TourApiRequest tourApiRequest = new TourApiRequest();
            tourApiRequest.setDefaultYn("Y");
            tourApiRequest.setContentId(tourInfoDTO.getContentId());

            DetailCommonResponse detailCommonResponse = detailCommon(tourApiRequest);

            if (detailCommonResponse.getPageInfo() != null) {
                DetailCommon detailCommon = detailCommonResponse.getDetailCommon().get(0);
                tourInfoDTO.setHomepage(detailCommon.getHomepage() != null ? detailCommon.getHomepage() : "");
                tourInfoDTO.setOverview(detailCommon.getOverview() != null ? detailCommon.getOverview() : "");
                tourInfoDTO.setTel(detailCommon.getTel() != null ? detailCommon.getTel() : "");
                tourInfoRepository.save(TourInfo.of(tourInfoDTO));
            }
        } catch (Exception e) {
            logger.error("updateTourInfoFromTourAPI : " + e.getMessage());
        }
    }

    private boolean saveTodayTotalCount(int totalCount) {
        String key = "total_" + StaticHelper.dateToString(new Date(), "yyyyMMdd");

        if (redisService.save(key, totalCount + "")) {
            // 해당 정보는 이틀 뒤에 레디스에서 삭제
            return redisService.expire(key, 2, TimeUnit.DAYS);
        }

        return false;
    }

    private int getYesterdayTotalCount() {
        try {
            String date = "total_" + StaticHelper.subDateToString(1, "yyyyMMdd");
            String totalCount = redisService.get(date);
            return !totalCount.equals("") ? Integer.parseInt(totalCount) : 0;
        } catch (Exception e) {
            logger.error("getYesterdayTotalCount : " + e.getMessage());
            return 0;
        }
    }

}
