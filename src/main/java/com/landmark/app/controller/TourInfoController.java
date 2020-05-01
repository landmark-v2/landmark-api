package com.landmark.app.controller;

import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.landmark.app.utils.constants.Constants.TOUR_INQUIRY_API;

@RestController
@RequestMapping(value = TOUR_INQUIRY_API)
public class TourInfoController extends LoggerUtils {
    private TourInquiryService tourInquiryService;

    @Autowired
    public TourInfoController(TourInquiryService tourInquiryService) {
        this.tourInquiryService = tourInquiryService;
    }

}
