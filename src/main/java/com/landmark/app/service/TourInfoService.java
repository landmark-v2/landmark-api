package com.landmark.app.service;

import com.landmark.app.model.dto.TourInfoDTO;

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
    void updateTours(TourInfoDTO tourInfoDTO, int userId);

    /**
     * 관광지 삭제
     */
    void deleteTours(int id, int user_id);

}
