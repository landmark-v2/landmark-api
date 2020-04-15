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

    List<TourReviewDTO.RecentReview> getRecentHistories(int userId);

}
