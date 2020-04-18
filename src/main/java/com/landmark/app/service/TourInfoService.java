package com.landmark.app.service;

import com.landmark.app.model.dto.TourInfoDTO;

import java.util.List;

public interface TourInfoService {

    List<Integer> findAllIdByTitle(String title);

    List<Integer> findAllIdByUserId(int userId);

    List<TourInfoDTO.RegisteredTourInfoDTO> getRegisteredTourInfoDTO(int userId);

}
