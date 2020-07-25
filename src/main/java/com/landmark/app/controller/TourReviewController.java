package com.landmark.app.controller;

import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.service.TourReviewService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.AccountHelper;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.landmark.app.utils.constants.Constants.*;

@RestController
@RequestMapping(value = TOUR_REVIEW_API)
public class TourReviewController extends LoggerUtils {

    private TourReviewService tourReviewService;
    private AccountHelper accountHelper;

    @Autowired
    public TourReviewController(TourReviewService tourReviewService, AccountHelper accountHelper) {
        this.tourReviewService = tourReviewService;
        this.accountHelper = accountHelper;
    }

    /**
     * 여행 후기 최신순 10개 조회
     */
    @GetMapping(value = "/recent")
    public ResponseEntity<?> getRecentReviews(HttpServletRequest request) {
        try {
            int userId = accountHelper.getAccountId(request);
            return new ResponseEntity<>(tourReviewService.getRecentReviews(userId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getRecentReviews : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 사용자의 지역별 후기 개수 -> 지역별 전체 조회로 수정
     */
    @GetMapping(value = "/count")
    public ResponseEntity<?> countByAreaCode(HttpServletRequest request) {
        try {
            int userId = accountHelper.getAccountId(request);
            return new ResponseEntity<>(tourReviewService.countAllByUserIdGroupByAreaCode(userId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("countByAreaCode : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 여행 후기 등록
     */
    @PostMapping
    public ResponseEntity<?> registerTourReview(@RequestBody TourReviewDTO tourReviewDTO, HttpServletRequest request) {
        try {
            int userId = accountHelper.getAccountId(request);
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
    @PostMapping(value = "/search")
    public ResponseEntity<?> getAllReviews(@RequestBody TourReviewDTO.SearchReviewDTO searchReviewDTO, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            int userId = user.getId();
            String roleName = user.getRole().getRolename();
            return new ResponseEntity<>(tourReviewService.getReviewList(userId, roleName, searchReviewDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getAllReviews : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 여행 후기 수정
     */
    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody TourReviewDTO.UpdateReviewDTO updateReviewDTO) {
        try {
            return new ResponseEntity<>(tourReviewService.updateReview(updateReviewDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateReview : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 여행 후기 삭제
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            String role = user.getRole().getRolename();

            if (role.equalsIgnoreCase(ROLE_ADMIN) || role.equalsIgnoreCase(ROLE_USER)) {
                // 여행지 관리자는 본인의 여행지의 후기만 삭제 가능
                // 일반 사용자는 본인이 쓴 여행지 후기만 삭제 가능
                return new ResponseEntity<>(tourReviewService.deleteReview(user.getId(), role, id), HttpStatus.OK);
            } else {
                // 개발자는 모두 삭제 가능
                return new ResponseEntity<>(tourReviewService.deleteReview(id), HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("updateReview : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getReview(@PathVariable int id) {
        try {
            return new ResponseEntity<>(tourReviewService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * area_code 로 areaName, sigunguName 조회
     */
    @GetMapping(value = "/names/{areaCode}")
    public ResponseEntity<?> getCodeNames(@PathVariable int areaCode, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            int userId = user.getId();
            return new ResponseEntity<>(tourReviewService.getCodeNames(userId, areaCode), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getCodeNames : " + e.getMessage());
            return new ResponseEntity<>(new JSONArray(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
