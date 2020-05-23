package com.landmark.app.service.impl;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.domain.TourReview;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.service.TourInfoService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.landmark.app.utils.constants.Constants.ROLE_ADMIN;
import static com.landmark.app.utils.constants.Constants.ROLE_DEV;

@Service
public class TourInfoServiceImpl extends LoggerUtils implements TourInfoService {

    private TourInfoRepository tourInfoRepository;

    @Autowired
    public TourInfoServiceImpl(TourInfoRepository tourInfoRepository) {
        this.tourInfoRepository = tourInfoRepository;
    }

    @Override
    public List<Integer> findAllIdByTitle(String title) {
        List<Integer> tourIds = new ArrayList<>();

        try {
            List<TourInfo> tourInfos = tourInfoRepository.findByTitleContaining(title);

            if (!tourInfos.isEmpty()) {
                for (TourInfo tourInfo : tourInfos) {
                    tourIds.add(tourInfo.getId());
                }
            }
        } catch (Exception e) {
            logger.error("findAllIdByTitle : " + e.getMessage());
        }

        return tourIds;
    }

    @Override
    public List<Integer> findAllIdByUserId(int userId) {
        List<Integer> tourIds = new ArrayList<>();

        try {
            List<TourInfo> tourInfos = tourInfoRepository.findAllByUserIdOrderByCreatedTime(userId);

            if (!tourInfos.isEmpty()) {
                for (TourInfo tourInfo : tourInfos) {
                    tourIds.add(tourInfo.getId());
                }
            }
        } catch (Exception e) {
            logger.error("findAllIdByUserId : " + e.getMessage());
        }

        return tourIds;
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

    @Override
    public TourInfoDTO findById(int id) {
        try {
            TourInfo tourInfo = tourInfoRepository.findById(id);
            return TourInfoDTO.of(tourInfo);
        } catch (Exception e) {
            logger.error("findById : " + e.getMessage());
            return null;
        }
    }

    /**
     * 관광지 수정
     */
    @Override
    public TourInfoDTO updateTours(TourInfoDTO tourInfoDTO, int getuserId){
        try{
            int userId = tourInfoDTO.getUserId();
            if(getuserId == userId) {
                String addr1 = tourInfoDTO.getAddr1();
                String addr2 = tourInfoDTO.getAddr2();
                int areaCode = tourInfoDTO.getAreaCode();
                int sigunguCode = tourInfoDTO.getSigunguCode();
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

                tourInfoRepository.updateTourByUserId(addr1, addr2, areaCode, sigunguCode,
                        cat1, cat2, cat3,
                        contentId, contentTypeId,
                        tel, title, overview, modifiedTime,
                        id, userId);
            }
            return tourInfoDTO;

        } catch (Exception e){
            logger.error("updateTours : " + e.getMessage());
            return null;
        }
    }


    /**
     * 관광지 삭제
     */
    @Override
    public boolean deleteTours(int id, int user_id, String role){
        try{
            TourInfoDTO tourInfoDTO = TourInfoDTO.of(tourInfoRepository.findById(id));

            if(tourInfoDTO.getUserId() == user_id || role.equalsIgnoreCase(ROLE_DEV)){
                tourInfoRepository.deleteTourByIdUserId(id, user_id);
            }
            return true;
        } catch (Exception e){
            logger.error("deleteByUserIdId" + e.getMessage());
            return false;
        }
    }
}
