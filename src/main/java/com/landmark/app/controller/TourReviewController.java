package com.landmark.app.controller;

import com.landmark.app.service.TourReviewService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.landmark.app.utils.Constants.TOUR_REVIEW_API;

@RestController
@RequestMapping(value = TOUR_REVIEW_API)
public class TourReviewController extends LoggerUtils {

    private TourReviewService tourReviewService;

    @Autowired
    public TourReviewController(TourReviewService tourReviewService) {
        this.tourReviewService = tourReviewService;
    }

}
