package com.landmark.app.service.impl;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.dto.TourInfoDTO;
import com.landmark.app.model.repository.TourInfoRepository;
import com.landmark.app.service.TourInfoService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<Integer> findAllIdByTitle(String title) {
        List<Integer> tourIds = new ArrayList<>();

        try {
            List<TourInfo> tourInfos = tourInfoRepository.findByTitleContaining(title);

            if (!tourInfos.isEmpty()) {
                for (TourInfo tourInfo : tourInfos) {
                    tourIds.add(tourInfo.getId());
                }
            }
        } catch (Exception e) {
            logger.error("findAllIdByTitle : " + e.getMessage());
        }

        return tourIds;
    }

    @Override
    public List<Integer> findAllIdByUserId(int userId) {
        List<Integer> tourIds = new ArrayList<>();

        try {
            List<TourInfo> tourInfos = tourInfoRepository.findAllByUserIdOrderByCreatedTime(userId);

            if (!tourInfos.isEmpty()) {
                for (TourInfo tourInfo : tourInfos) {
                    tourIds.add(tourInfo.getId());
                }
            }
        } catch (Exception e) {
            logger.error("findAllIdByUserId : " + e.getMessage());
        }

        return tourIds;
    }

}
