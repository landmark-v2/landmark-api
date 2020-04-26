package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourInfoRepository extends JpaRepository<TourInfo, Integer> {

    List<TourInfo> findByTitleContaining(String title);

    List<TourInfo> findAllByUserIdOrderByCreatedTime(int userId);

    List<TourInfo> findAllByTelIsNullAndHomepageIsNullAndOverviewIsNull();

    Page<TourInfo> findAllByTelIsNullAndHomepageIsNullAndOverviewIsNull(Pageable pageable);

    /** SearchService **/
    Page<TourInfo> findAllByAreaCodeAndContentTypeId(int areaCode, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndContentTypeIdAndTitleContaining(int areaCode, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndContentTypeId(int areaCode, int sigunguCode, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndContentTypeIdAndTitleContaining(int areaCode, int sigunguCode, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeId(int areaCode, int sigunguCode, String cat1, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeIdAndTitleContaining(int areaCode, int sigunguCode, String cat1, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeId(int areaCode, int sigunguCode, String cat1, String cat2, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeIdAndTitleContaining(int areaCode, int sigunguCode, String cat1, String cat2, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeId(int areaCode, int sigunguCode, String cat1, String cat2, String cat3, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContaining(int areaCode, int sigunguCode, String cat1, String cat2, String cat3, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndCat1AndContentTypeId(int areaCode, String cat1, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndCat1AndContentTypeIdAndTitleContaining(int areaCode, String cat1, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndCat1AndCat2AndContentTypeId(int areaCode, String cat1, String cat2, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndCat1AndCat2AndContentTypeIdAndTitleContaining(int areaCode, String cat1, String cat2, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeId(int areaCode, String cat1, String cat2, String cat3, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContaining(int areaCode, String cat1, String cat2, String cat3, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByCat1AndContentTypeId(String cat1, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByCat1AndContentTypeIdAndTitleContaining(String cat1, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByCat1AndCat2AndContentTypeId(String cat1, String cat2, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByCat1AndCat2AndContentTypeIdAndTitleContaining(String cat1, String cat2, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByCat1AndCat2AndCat3AndContentTypeId(String cat1, String cat2, String cat3, int contentTypeId, Pageable pageable);
    Page<TourInfo> findAllByCat1AndCat2AndCat3AndContentTypeIdAndTitleContaining(String cat1, String cat2, String cat3, int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByAndContentTypeIdAndTitleContaining(int contentTypeId, String title, Pageable pageable);
    Page<TourInfo> findAllByContentTypeId(int contentTypeId, Pageable pageable);

}
