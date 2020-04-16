package com.landmark.app.service.impl;

import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.model.repository.TourReviewRepository;
import com.landmark.app.service.SearchService;
import com.landmark.app.service.TourReviewService;
import com.landmark.app.utils.Constants;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TourReviewServiceImpl extends LoggerUtils implements TourReviewService {

    private TourReviewRepository tourReviewRepository;
    private SearchService searchService;

    @Autowired
    public TourReviewServiceImpl(TourReviewRepository tourReviewRepository, SearchService searchService) {
        this.tourReviewRepository = tourReviewRepository;
        this.searchService = searchService;
    }

    @Override
    public List<TourReviewDTO.RecentReview> getRecentHistories(int userId) {
        List<TourReviewDTO.RecentReview> recentReviews = new ArrayList<>();

        try {
            List<TourReviewDTO> tourReviewDTOS = TourReviewDTO.of(tourReviewRepository.findTop10ByUserId(userId));

            for (TourReviewDTO tourReviewDTO : tourReviewDTOS) {
                Map<Integer, Integer> sigunguPkMap = new HashMap<>();
                sigunguPkMap.put(tourReviewDTO.getAreaCode(), tourReviewDTO.getSigunguCode());

                String areaName = Constants.areaCodeMap.get(tourReviewDTO.getAreaCode());
                String sigunguName = Constants.sigunguCodeMap.get(sigunguPkMap);

                recentReviews.add(getRecentReview(areaName, sigunguName, tourReviewDTO.getFirstImage()));
            }
        } catch (Exception e) {
            logger.error("getRecentHistories : " + e.getMessage());
        }

        return recentReviews;
    }

    private TourReviewDTO.RecentReview getRecentReview(String areaName, String sigunguName, String firstImage) {
        TourReviewDTO.RecentReview recentReview = new TourReviewDTO.RecentReview();
        recentReview.setAreaName(areaName);
        recentReview.setSigunguName(sigunguName);
        recentReview.setFirstImage(firstImage);
        return recentReview;
    }

    @Override
    public int countByAreaCode(int areaCode, int userId) {
        try {
            return tourReviewRepository.countAllByAreaCodeAndUserId(areaCode, userId);
        } catch (Exception e) {
            logger.error("countByAreaCode : " + e.getMessage());
            return 0;
        }
    }

    @Override
    public TourReviewDTO registerReview(TourReviewDTO tourReviewDTO) {
        TourReviewDTO savedTourReview = new TourReviewDTO();

        try {

        } catch (Exception e) {

        }

        return savedTourReview;
    }
}
