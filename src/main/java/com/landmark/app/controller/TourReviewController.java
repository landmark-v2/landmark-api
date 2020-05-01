package com.landmark.app.controller;

import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.service.TourReviewService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.landmark.app.utils.constants.Constants.TOUR_REVIEW_API;

@RestController
@RequestMapping(value = TOUR_REVIEW_API)
public class TourReviewController extends LoggerUtils {

    private TourReviewService tourReviewService;

    @Autowired
    public TourReviewController(TourReviewService tourReviewService) {
        this.tourReviewService = tourReviewService;
    }

    /**
     * 여행 후기 최신순 10개 조회
     */
    @GetMapping(value = "/recent")
    public ResponseEntity<?> getRecentReviews(HttpRequest request) {
        try {
            int userId = 0;     // TODO 스프링 시큐리티 적용 후 변경
            return new ResponseEntity<>(tourReviewService.getRecentReviews(userId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getRecentReviews : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 사용자의 지역별 후기 개수
     */
    @GetMapping(value = "/count")
    public ResponseEntity<?> countByAreaCode(@RequestParam int areaCode, HttpRequest request) {
        try {
            int userId = 0;     // TODO 스프링 시큐리티 적용 후 변경
            return new ResponseEntity<>(tourReviewService.countByAreaCode(areaCode, userId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("countByAreaCode : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 여행 후기 등록
     */
    @PostMapping
    public ResponseEntity<?> registerTourReview(@Valid @RequestBody TourReviewDTO tourReviewDTO, HttpRequest request) {
        try {
            int userId = 0;     // TODO 스프링 시큐리티 적용 후 변경
            tourReviewDTO.setUserId(userId);
            return new ResponseEntity<>(tourReviewService.registerReview(tourReviewDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("registerTourReview : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 여행 후기 전체 조회
     */
    @GetMapping
    public ResponseEntity<?> getAllReviews(@RequestBody TourReviewDTO.SearchReviewDTO searchReviewDTO, HttpRequest request) {
        try {
            UserDTO user = new UserDTO();   // TODO 스프링 시큐리티 적용 후 변경
            int userId = user.getId();
            String roleName = user.getRole().getRolename();
            return new ResponseEntity<>(tourReviewService.getReviewList(userId, roleName, searchReviewDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getAllReviews : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
