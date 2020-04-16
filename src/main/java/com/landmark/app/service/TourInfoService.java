package com.landmark.app.service;

import com.landmark.app.model.dto.TourInfoDTO;

import java.util.List;

public interface TourInfoService {

    List<TourInfoDTO> findAllByKeyword(String keyword);

}
