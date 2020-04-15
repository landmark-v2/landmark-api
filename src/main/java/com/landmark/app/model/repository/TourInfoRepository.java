package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourInfoRepository extends JpaRepository<TourInfo, Integer> {

    TourInfo findById(int id);

}
