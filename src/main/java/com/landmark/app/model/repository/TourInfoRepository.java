package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourInfoRepository extends JpaRepository<TourInfo, Integer> {

    List<TourInfo> findByTitleContaining(String title);

    List<TourInfo> findAllByUserIdOrderByCreatedTime(int userId);

    Page<TourInfo> findAllByTitleContainingOrderByCreatedTimeDesc(String keyword, Pageable pageable);

}
