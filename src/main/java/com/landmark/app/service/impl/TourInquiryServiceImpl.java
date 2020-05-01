package com.landmark.app.service.impl;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.service.TourInquiryService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TourInquiryServiceImpl extends LoggerUtils implements TourInquiryService {

    private TourInfoRepository tourInfoRepository;

    @Autowired
    public TourInquiryServiceImpl(TourInfoRepository tourInfoRepository){
        this.tourInfoRepository = tourInfoRepository;
    }

    @Override
    public TourInfoDTO save(TourInfoDTO tourInfoDTO){
        try{
            tourInfoDTO.setModifiedTime(new Date());
            TourInfo tourInfo = tourInfoRepository.saveAndFlush(TourInfo.of(tourInfoDTO));
            return TourInfoDTO.of(tourInfo);
        } catch (Exception e){
            logger.error("save : " + e.getMessage());
            return null;
        }
    }

    /**
     * 관광지 등록
     */
    public TourInfoDTO registerTourist(TourInfoDTO tourInfoDTO){
        tourInfoDTO.setCreatedTime(new Date());
        return save(tourInfoDTO);
    }

    /**
     * 관광지 수정
     */
    @Override
    public void updateTours(TourInfoDTO tourInfoDTO){
        try{
            String addr1 = tourInfoDTO.getAddr1();
            String addr2 = tourInfoDTO.getAddr2();
            int areaCode = tourInfoDTO.getAreaCode();
            int sigunguCode= tourInfoDTO.getSigunguCode();
            String cat1 = tourInfoDTO.getCat1();
            String cat2 = tourInfoDTO.getCat2();
            String cat3 = tourInfoDTO.getCat3();
            int contentId = tourInfoDTO.getContentId();
            int contentTypeId = tourInfoDTO.getContentTypeId();
            String tel = tourInfoDTO.getTel();
            String title = tourInfoDTO.getTitle();
            String overview = tourInfoDTO.getOverview();
            Date modifiedTime = new Date();
            int id = tourInfoDTO.getId();
            int userId = tourInfoDTO.getUserId();

            tourInfoRepository.updateTourByUserId(addr1, addr2, areaCode, sigunguCode,
                    cat1, cat2, cat3,
                    contentId,contentTypeId,
                    tel, title, overview, modifiedTime,
                    id, userId);

        } catch (Exception e){
            logger.error("updateTours : " + e.getMessage());
        }
    }



    /**
     * 관광지 조회
     */
    @Override
    public List<TourInfoDTO.RegisteredTourInfoDTO> getTours(int user_id){
        try{

        } catch (Exception e){
            return null;
        }
        return null;
    }

    /**
     * 관광지 삭제
     */
    @Override
    public void deleteTours(int id, int user_id){
        try{
            tourInfoRepository.deleteTourByIdUserId(id, user_id);
        } catch (Exception e){
            logger.error("deleteByUserIdId" + e.getMessage());
        }
    }
}
