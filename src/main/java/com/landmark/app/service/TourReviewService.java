package com.landmark.app.service;

import com.landmark.app.model.dto.TourReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TourReviewService {

    /**
     * 저장
     */
    TourReviewDTO save(TourReviewDTO tourReviewDTO);

    /**
     * 여행 후기 최신순 10개 조회
     */
    List<TourReviewDTO.RecentReview> getRecentReviews(int userId);

    /**
     * 사용자의 지역별 후기 개수
     */
    int countByAreaCode(int areaCode, int userId);

    /**
     * 여행 후기 등록
     */
    TourReviewDTO registerReview(TourReviewDTO tourReviewDTO);

    /**
     * 여행 후기 전체 조회
     */
    Page<TourReviewDTO> getReviewList(int userId, String roleName, TourReviewDTO.SearchReviewDTO searchReviewDTO);

    /**
     * file 정보 가져오기
     */
    TourReviewDTO.FileDTO findFileInfoById(int id);

    /**
     * file 정보 저장
     */
    TourReviewDTO.FileDTO saveFile(TourReviewDTO.FileDTO fileDTO);

}
