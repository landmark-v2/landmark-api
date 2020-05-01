package com.landmark.app.service;

import com.landmark.app.model.dto.TourInfoDTO;

import java.util.List;

public interface TourInquiryService {

    TourInfoDTO save(TourInfoDTO tourInfoDTO);

    /**
     * 관광지 등록
     */
    TourInfoDTO registerTourist(TourInfoDTO tourInfoDTO);

    /**
     * 관광지 수정
     */
    void updateTours(TourInfoDTO tourInfoDTO);

    /**
     * 관광지 조회 -> 보현이가 구현함
     */
    List<TourInfoDTO.RegisteredTourInfoDTO> getTours(int user_id);

    /**
     * 관광지 삭제
     */
    void deleteTours(int id, int user_id);
}
