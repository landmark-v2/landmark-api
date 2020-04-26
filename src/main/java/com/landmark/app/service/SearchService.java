package com.landmark.app.service;

import com.landmark.app.model.dto.SearchTourInfoDTO;
import com.landmark.app.model.dto.TourInfoDTO;
import org.springframework.data.domain.Page;

public interface SearchService {

    Page<TourInfoDTO> searchTourInfo(SearchTourInfoDTO searchDTO) throws Exception;

}
