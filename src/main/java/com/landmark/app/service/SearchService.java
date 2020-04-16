package com.landmark.app.service;

import com.landmark.app.model.dto.SearchDTO;
import com.landmark.app.model.dto.TourInfoDTO;
import org.springframework.data.domain.Page;

public interface SearchService {

    /**
     * 1. 키워드로 조회
     * 2. 지역 조회
     * 3. 행사 조회
     * 4. 상세정보 조회
     * 5. 키워드 있으면 1 없으면 2
     */

    /**
     * 키워드로 조회
     */
    Page<TourInfoDTO> searchKeyword(SearchDTO.SearchKeywordDTO searchKeywordDTO);

}
