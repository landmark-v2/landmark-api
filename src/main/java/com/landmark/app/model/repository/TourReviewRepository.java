package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface TourReviewRepository extends JpaRepository<TourReview, Integer> {

    @Query(value = "select * from TOUR_REVIEW where user_id=:userId order by modified_time desc limit 10", nativeQuery = true)
    List<TourReview> findTop10ByUserId(@Param("userId") int userId);

    TourReview findById(int id);

    @Modifying
    @Transactional
    void deleteByIdAndUserId(int id, int userId);

    @Modifying
    @Transactional
    void deleteById(int id);

    // search review - role_user
    List<TourReview> findAllByUserIdOrderByCreatedTimeDesc(int userId);
    List<TourReview> findAllByUserIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(int userId, Date startDate, Date endDate);
    List<TourReview> findAllByUserIdAndTitleContainingOrderByCreatedTimeDesc(int userId, String title);
    List<TourReview> findAllByUserIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(int userId, String title, Date startDate, Date endDate);

    // search review - role_admin
    List<TourReview> findAllByTourIdOrderByCreatedTimeDesc(int tourId);
    List<TourReview> findAllByTourIdAndCreatedTimeBetweenOrderByCreatedTimeDesc(int tourId, Date startDate, Date endDate);
    List<TourReview> findAllByTourIdAndTitleContainingOrderByCreatedTimeDesc(int tourId, String title);
    List<TourReview> findAllByTourIdAndTitleContainingAndCreatedTimeBetweenOrderByCreatedTimeDesc(int tourId, String title, Date startDate, Date endDate);

    // search review - role_dev
    List<TourReview> findAllByOrderByCreatedTimeDesc();
    List<TourReview> findAllByCreatedTimeBetweenOrderByCreatedTimeDesc(Date startDate, Date endDate);

    int countAllByUserIdAndAreaCode(int userId, int areaCode);

}
