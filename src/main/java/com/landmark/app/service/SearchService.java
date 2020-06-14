package com.landmark.app.service;

import com.landmark.app.model.dto.SearchTourInfoDTO;
import com.landmark.app.model.dto.TourInfoDTO;

import java.util.List;

public interface SearchService {

    List<TourInfoDTO> searchTourInfo(SearchTourInfoDTO searchDTO) throws Exception;

}
