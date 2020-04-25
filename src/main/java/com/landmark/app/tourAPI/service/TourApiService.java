package com.landmark.app.tourAPI.service;

import com.landmark.app.tourAPI.dto.request.TourApiRequest;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedListResponse;
import com.landmark.app.tourAPI.dto.response.detailCommon.DetailCommonResponse;
import org.springframework.stereotype.Service;

@Service
public interface TourApiService {

    /**
     * TourAPI 에서 조회된 데이터를 디비에 저장하고 저장된 개수를 리턴
     */
    int saveTourInfoFromTourAPI();

    /**
     * 지역기반 관광정보 조회
     */
    AreaBasedListResponse areaBasedList(int page, int size, TourApiRequest tourApiRequest);

    /**
     * 공통정보 조회
     */
    DetailCommonResponse detailCommon(TourApiRequest tourApiRequest);

}
