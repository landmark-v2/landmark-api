package com.landmark.app.controller;

import com.landmark.app.service.TourInfoService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.landmark.app.utils.constants.Constants.TOUR_INFO_API;

@RestController
@RequestMapping(value = TOUR_INFO_API)
public class TourInfoController extends LoggerUtils {
    private TourInfoService tourInfoService;

    @Autowired
    public TourInfoController(TourInfoService tourInfoService) {
        this.tourInfoService = tourInfoService;
    }

}
