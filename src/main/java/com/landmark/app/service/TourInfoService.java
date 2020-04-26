package com.landmark.app.service;

import java.util.List;

public interface TourInfoService {

    /**
     * 관광지명으로 모든 관광지 정보 id 조회
     */
    List<Integer> findAllIdByTitle(String title);

    /**
     * 사용자 id 로 모든 관광지 정보 id 조회
     */
    List<Integer> findAllIdByUserId(int userId);

}
