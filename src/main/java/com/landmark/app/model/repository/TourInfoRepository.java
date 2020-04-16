package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourInfoRepository extends JpaRepository<TourInfo, Integer> {

    List<TourInfo> findAllByTitleContainingOrderByCreatedTimeDesc(String keyword);

}
