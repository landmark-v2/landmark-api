package com.landmark.app.service.impl;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.domain.TourReview;
import com.landmark.app.model.domain.comment.InfoComment;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.model.dto.commnet.InfoCommentDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.model.repository.comment.InfoCommentRepository;
import com.landmark.app.service.TourInfoService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.StaticHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.landmark.app.utils.constants.Constants.ROLE_ADMIN;
import static com.landmark.app.utils.constants.Constants.ROLE_DEV;

@Service
public class TourInfoServiceImpl extends LoggerUtils implements TourInfoService {

    private TourInfoRepository tourInfoRepository;
    private InfoCommentRepository commentRepository;

    @Autowired
    public TourInfoServiceImpl(TourInfoRepository tourInfoRepository, InfoCommentRepository commentRepository) {
        this.tourInfoRepository = tourInfoRepository;
        this.commentRepository = commentRepository;
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
            TourInfo tourInfo = new TourInfo();
            tourInfo.setAddr1(tourInfoDTO.getAddr1());
            tourInfo.setAddr2(tourInfoDTO.getAddr2());
            tourInfo.setAreaCode(tourInfoDTO.getAreaCode());
            tourInfo.setCat1(tourInfoDTO.getCat1());
            tourInfo.setCat2(tourInfoDTO.getCat2());
            tourInfo.setCat3(tourInfoDTO.getCat3());
            tourInfo.setContentId(tourInfoDTO.getContentId());
            tourInfo.setContentTypeId(tourInfoDTO.getContentTypeId());
            tourInfo.setCreatedTime(StaticHelper.stringToDate(tourInfoDTO.getCreatedTime(), "yyyy-MM-dd HH:mm"));
            tourInfo.setModifiedTime(new Date());
            tourInfo.setOverview(tourInfoDTO.getOverview());
            tourInfo.setTel(tourInfoDTO.getTel());
            tourInfo.setHomepage(tourInfoDTO.getHomepage());
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

    /**
     * 관광지 댓글
     */

    @Override
    public List<InfoCommentDTO> findAllInfoComments(int infoId) {
        try {
            return InfoCommentDTO.of(commentRepository.findAllByinfoId(infoId));
        } catch (Exception e) {
            logger.error("findAllInfoComments : " + e.getMessage());
            return null;
        }
    }

    @Override
    public InfoCommentDTO registerInfoComment(InfoCommentDTO commentDTO) {
        try {
            InfoComment comment = new InfoComment();
            comment.setInfoId(commentDTO.getInfoId());
            comment.setUserId(commentDTO.getUserId());
            comment.setComment(commentDTO.getComment());
            comment.setCreatedTime(new Date());
            comment.setModifiedTime(new Date());
            return InfoCommentDTO.of(commentRepository.save(comment));
        } catch (Exception e) {
            logger.error("register Info Comment : " + e.getMessage());
            return null;
        }
    }

    @Override
    public InfoCommentDTO updateInfoComment(InfoCommentDTO commentDTO, int userId) {
        try {
            if(userId == commentDTO.getUserId()) {
                InfoComment comment = commentRepository.findById(commentDTO.getId());

                if(!StringUtils.isEmpty(commentDTO.getComment())) {
                    comment.setComment(commentDTO.getComment());
                }

                comment.setModifiedTime(new Date());
                return InfoCommentDTO.of(commentRepository.save(comment));
            }
        } catch (Exception e) {
            logger.error("update Info Comment : " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteInfoCommet(int id, int user_id, String role) {
        try {
            if(ROLE_DEV.equals(role)) {
                commentRepository.deleteById(id);
                return true;
            }

            InfoComment commnet = commentRepository.findById(id);
            if(commnet.getUserId() == user_id) {
                commentRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            logger.error("delete Info Comment : " + e.getMessage());
        }
        return false;
    }
}
