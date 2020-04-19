package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TourReviewRepository extends JpaRepository<TourReview, Integer> {

    @Query(value = "select * from TOUR_REVIEW where user_id=:userId order by modified_time desc limit 10", nativeQuery = true)
    List<TourReview> findTop10ByUserId(@Param("userId") int userId);

    int countAllByAreaCodeAndUserId(int areaCode, int userId);

    TourReview findById(int id);

    // search review - role_user
    Page<TourReview> findAllByUserIdOrderByCreatedTimeDesc(int userId, Pageable pageable);
    Page<TourReview> findAllByUserIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(int userId, Date startDate, Date endDate, Pageable pageable);
    Page<TourReview> findAllByUserIdAndTitleContainingOrderByCreatedTimeDesc(int userId, String title, Pageable pageable);
    Page<TourReview> findAllByUserIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(int userId, String title, Date startDate, Date endDate, Pageable pageable);

    // search review - role_admin
    List<TourReview> findAllByTourIdOrderByCreatedTimeDesc(int tourId);
    List<TourReview> findAllByTourIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(int tourId, Date startDate, Date endDate);
    List<TourReview> findAllByTourIdAndTitleContainingOrderByCreatedTimeDesc(int tourId, String title);
    List<TourReview> findAllByTourIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(int tourId, String title, Date startDate, Date endDate);
    Page<TourReview> findAllByTourIdAndTitleContainingOrderByCreatedTimeDesc(int tourId, String title, Pageable pageable);
    Page<TourReview> findAllByTourIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(int tourId, String title, Date startDate, Date endDate, Pageable pageable);

    // search review - role_dev
    Page<TourReview> findAllByOrderByCreatedTimeDesc(Pageable pageable);
    Page<TourReview> findAllByCreatedTimeBetweenOrderByCreatedTimeDesc(Date startDate, Date endDate, Pageable pageable);


}
