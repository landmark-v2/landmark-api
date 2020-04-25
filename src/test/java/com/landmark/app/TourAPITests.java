package com.landmark.app;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.tourAPI.dto.request.TourApiRequest;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedList;
import com.landmark.app.tourAPI.dto.response.areaBasedList.AreaBasedListResponse;
import com.landmark.app.tourAPI.dto.response.detailCommon.DetailCommon;
import com.landmark.app.tourAPI.dto.response.detailCommon.DetailCommonResponse;
import com.landmark.app.tourAPI.service.TourApiService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TourAPITests {

    private TourApiService tourApiService;
    private TourInfoRepository tourInfoRepository;

    @Autowired
    public TourAPITests(TourApiService tourApiService, TourInfoRepository tourInfoRepository) {
        this.tourApiService = tourApiService;
        this.tourInfoRepository = tourInfoRepository;
    }

    /** 실행완료 - 한번만 디비에 모든 TourAPI 데이터를 저장하기 위함 **/
    @Test
    @Ignore
    public void saveTourInfoFromTourAPI() {
        try {
            TourApiRequest tourApiRequest = new TourApiRequest();
            tourApiRequest.setListYN("Y");
            tourApiRequest.setArrange("D"); // 생성일자 순으로 정렬
            AreaBasedListResponse areaBasedListResponse = tourApiService.areaBasedList(1, 25331, tourApiRequest);
            List<AreaBasedList> areaBasedList = areaBasedListResponse.getAreaBasedList();

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
                tourInfoDTO.setUserId(0);
                tourInfoRepository.save(TourInfo.of(tourInfoDTO));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 하루 트래픽 1000건으로 나누어서 실행 - 한번만 디비에 모든 TourAPI 데이터를 저장하기 위함 (homepage, overview)
     * **/
    @Test
    @Ignore
    public void updateTourInfoFromTourAPI() {
        try {
            List<TourInfo> tourInfos = tourInfoRepository.findAllByTelIsNullAndHomepageIsNullAndOverviewIsNull();

            for (TourInfo tourInfo : tourInfos) {
                int contentId = tourInfo.getContentId();
                TourApiRequest tourApiRequest = new TourApiRequest();
                tourApiRequest.setDefaultYn("Y");
                tourApiRequest.setContentId(contentId);

                DetailCommonResponse detailCommonResponse = tourApiService.detailCommon(tourApiRequest);

                if (detailCommonResponse.getDetailCommon() != null) {
                    DetailCommon detailCommon = detailCommonResponse.getDetailCommon().get(0);
                    tourInfo.setHomepage(detailCommon.getHomepage() != null ? detailCommon.getHomepage() : "");
                    tourInfo.setOverview(detailCommon.getOverview() != null ? detailCommon.getOverview() : "");
                    tourInfo.setTel(detailCommon.getTel() != null ? detailCommon.getTel() : "");
                }
            }

            tourInfoRepository.saveAll(tourInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void detailCommonTest() {
        try {
            TourApiRequest tourApiRequest = new TourApiRequest();
            tourApiRequest.setDefaultYn("Y");
            tourApiRequest.setContentId(126918);
            DetailCommon detailCommon = tourApiService.detailCommon(tourApiRequest).getDetailCommon().get(0);
            System.out.println(detailCommon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
