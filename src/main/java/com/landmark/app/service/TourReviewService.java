package com.landmark.app.service;

import com.landmark.app.model.dto.TourReviewDTO;

import java.util.List;

public interface TourReviewService {

    /**
     * 1. 최신순으로 여행후기 10개 조회 - response List<지역명, 시군구명, 이미지>
     * 2. 지역별 후기 개수 - request int areaCode
     * 3. 여행후기 등록 - response TourReviewDTO
     * 4. 사용자의 여행후기 리스트 조회 - response Page<전체>
     */

    /**
     * 여행 후기 최신순 10개 조회
     * @return RecentReview(areaName, sigunguName, firstImage)
     */
    List<TourReviewDTO.RecentReview> getRecentHistories(int userId);

    /**
     * 사용자의 지역별 후기 개수
     */
    int countByAreaCode(int areaCode, int userId);

    /**
     * 여행 후기 등록
     */
    TourReviewDTO registerReview(TourReviewDTO tourReviewDTO);

}
