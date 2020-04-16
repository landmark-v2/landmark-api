package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourReviewRepository extends JpaRepository<TourReview, Integer> {

    @Query(value = "select * from TOUR_REVIEW where user_id=:userId order by modified_time desc limit 10", nativeQuery = true)
    List<TourReview> findTop10ByUserId(@Param("userId") int userId);

    int countAllByAreaCodeAndUserId(int areaCode, int userId);

}
