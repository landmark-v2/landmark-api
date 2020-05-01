package com.landmark.app.controller;

import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.service.TourInquiryService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.landmark.app.utils.constants.Constants.TOUR_INQUIRY_API;

@RestController
@RequestMapping(value = TOUR_INQUIRY_API)
public class TourInquiryController extends LoggerUtils {
    private TourInquiryService tourInquiryService;

    @Autowired
    public TourInquiryController(TourInquiryService tourInquiryService) {
        this.tourInquiryService = tourInquiryService;
    }

}
