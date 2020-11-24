package com.landmark.app.service;

import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.dto.commnet.InfoCommentDTO;

import java.util.List;

public interface TourInfoService {

    /**
     * 관광지명으로 모든 관광지 정보 id 조회
     */
    List<Integer> findAllIdByTitle(String title);

    /**
     * 사용자 id 로 모든 관광지 정보 id 조회
     */
    List<Integer> findAllIdByUserId(int userId);

    TourInfoDTO findById(int id);

    TourInfoDTO save(TourInfoDTO tourInfoDTO);

    /**
     * 관광지 등록
     */
    TourInfoDTO registerTourist(TourInfoDTO tourInfoDTO);

    /**
     * 관광지 수정
     */
    TourInfoDTO updateTours(TourInfoDTO tourInfoDTO, int userId);

    /**
     * 관광지 삭제
     */
    boolean deleteTours(int id, int user_id, String role);


    /** 댓글 가져오기 */
    List<InfoCommentDTO> findAllInfoComments(int infoId);

    /** 댓글 등록 */
    InfoCommentDTO registerInfoComment(InfoCommentDTO commentDTO);

    /** 댓글 수정 */
    InfoCommentDTO updateInfoComment(InfoCommentDTO commentDTO, int userId);

    /** 댓글 삭제 */
    boolean deleteInfoCommet(int id, int user_id, String role);
}
