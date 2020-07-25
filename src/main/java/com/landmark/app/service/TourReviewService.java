package com.landmark.app.service;

import com.landmark.app.model.dto.TourReviewDTO;
import org.json.simple.JSONArray;

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
     * - int -> JSONArray 변경 (전체 지역의 리뷰 카운트 조회)
     */
    JSONArray countAllByUserIdGroupByAreaCode(int userId);

    /**
     * 여행 후기 등록
     */
    TourReviewDTO registerReview(TourReviewDTO tourReviewDTO);

    /**
     * 여행 후기 전체 조회
     */
    List<TourReviewDTO> getReviewList(int userId, String roleName, TourReviewDTO.SearchReviewDTO searchReviewDTO);

    /**
     * 여행 후기 조회
     */
    TourReviewDTO findById(int id);

    /**
     * 여행 후기 수정
     */
    TourReviewDTO updateReview(TourReviewDTO.UpdateReviewDTO updateReviewDTO);

    /**
     * 여행 후기 삭제 - 관리자, 일반 사용자
     */
    boolean deleteReview(int userId, String role, int reviewId);

    /**
     * 여행 후기 삭제 - 개발자
     */
    boolean deleteReview(int reviewId);

    /**
     * file 정보 가져오기
     */
    TourReviewDTO.FileDTO findFileInfoById(int id);

    /**
     * file 정보 저장
     */
    TourReviewDTO.FileDTO saveFile(TourReviewDTO.FileDTO fileDTO);

    /**
     * userId, areaCode 로 후기 쓴 codeName 조회
     */
    JSONArray getCodeNames(int userId, int areaCode);

}
