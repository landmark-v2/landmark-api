package com.landmark.app.service.impl;

import com.landmark.app.model.dto.SearchDTO;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.service.SearchService;
import com.landmark.app.service.TourInfoService;
import com.landmark.app.tourAPI.TourApiService;
import com.landmark.app.tourAPI.dto.request.TourApiRequest;
import com.landmark.app.tourAPI.dto.response.searchKeyword.SearchKeyword;
import com.landmark.app.tourAPI.dto.response.searchKeyword.SearchKeywordResponse;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.StaticHelper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl extends LoggerUtils implements SearchService {

    private TourApiService tourApiService;
    private TourInfoService tourInfoService;

    @Autowired
    public SearchServiceImpl(TourApiService tourApiService, TourInfoService tourInfoService) {
        this.tourApiService = tourApiService;
        this.tourInfoService = tourInfoService;
    }

    @Override
    public Page<TourInfoDTO> searchKeyword(SearchDTO.SearchKeywordDTO searchKeywordDTO) {
        List<TourInfoDTO> tourInfoDTOS = new ArrayList<>();

        int page = searchKeywordDTO.getPage() != 0 ? searchKeywordDTO.getPage() : 1;
        int size = searchKeywordDTO.getSize() != 0 ? searchKeywordDTO.getSize() : 10;
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdTime"));
        Pageable pageable = PageRequest.of(page, size);

        try {
            // 1. tourAPI 키워드로 조회
            TourApiRequest tourApiRequest = convertToTourApiRequest(searchKeywordDTO);
            SearchKeywordResponse searchKeywordResponse = tourApiService.searchKeyword(page, size, tourApiRequest);
            tourInfoDTOS.addAll(searchKeywordConvertToTourInfoDTOS(searchKeywordResponse.getSearchKeyword()));

            // 2. TOUR_INFO 디비에서 조회
            tourInfoDTOS.addAll(tourInfoService.findAllByKeyword(searchKeywordDTO.getKeyword()));
        } catch (Exception e) {
            logger.error("searchKeyword : " + e.getMessage());
        }

        // 3. page 설정, Page<TourInfoDTO> 형식으로 리턴
        return new PageImpl<>(tourInfoDTOS, pageable, tourInfoDTOS.size());
    }

    private TourApiRequest convertToTourApiRequest(SearchDTO.SearchKeywordDTO searchKeywordDTO) {
        String listYN = "Y";
        String arrange = StaticHelper.getInfoValue(searchKeywordDTO.getArrange(), "D");
        int contentTypeId = searchKeywordDTO.getContentTypeId();
        int areaCode = searchKeywordDTO.getAreaCode();
        int sigunguCode = searchKeywordDTO.getSigunguCode();
        String cat1 = StaticHelper.getInfoValue(searchKeywordDTO.getCat1(), "");
        String cat2 = StaticHelper.getInfoValue(searchKeywordDTO.getCat2(), "");;
        String cat3 = StaticHelper.getInfoValue(searchKeywordDTO.getCat3(), "");;
        String keyword = StaticHelper.getInfoValue(searchKeywordDTO.getKeyword(), "");

        TourApiRequest tourApiRequest = new TourApiRequest();
        tourApiRequest.setListYN(listYN);
        tourApiRequest.setArrange(arrange);
        tourApiRequest.setContentTypeId(contentTypeId);
        tourApiRequest.setAreaCode(areaCode);
        tourApiRequest.setSigunguCode(sigunguCode);
        tourApiRequest.setCat1(cat1);
        tourApiRequest.setCat2(cat2);
        tourApiRequest.setCat3(cat3);
        tourApiRequest.setKeyword(keyword);

        return tourApiRequest;
    }

    private List<TourInfoDTO> searchKeywordConvertToTourInfoDTOS(List<SearchKeyword> searchKeywords) {
        List<TourInfoDTO> tourInfoDTOS = MapperUtils.convert(searchKeywords, new TypeToken<List<TourInfoDTO>>(){}.getType());

        if (tourInfoDTOS != null) {
            return tourInfoDTOS;
        } else {
            return new ArrayList<>();
        }
    }
}
