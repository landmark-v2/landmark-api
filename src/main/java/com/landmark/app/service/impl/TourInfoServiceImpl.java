package com.landmark.app.service.impl;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.service.TourInfoService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourInfoServiceImpl extends LoggerUtils implements TourInfoService {

    private TourInfoRepository tourInfoRepository;

    @Autowired
    public TourInfoServiceImpl(TourInfoRepository tourInfoRepository) {
        this.tourInfoRepository = tourInfoRepository;
    }

    @Override
    public List<TourInfoDTO> findAllByKeyword(String keyword) {
        List<TourInfoDTO> tourInfoDTOS = new ArrayList<>();

        try {
            List<TourInfo> tourInfos = tourInfoRepository.findAllByTitleContainingOrderByCreatedTimeDesc(keyword);
            tourInfoDTOS = TourInfoDTO.of(tourInfos);
        } catch (Exception e) {
            logger.error("findAllByKeyword : " + e.getMessage());
        }

        return tourInfoDTOS;
    }
}
