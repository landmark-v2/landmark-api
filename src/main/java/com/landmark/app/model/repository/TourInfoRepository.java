package com.landmark.app.model.repository;

import com.landmark.app.model.domain.TourInfo;
import com.landmark.app.model.dto.TourInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    @Override
    Optional<TourInfo> findById(Integer id);

    @Query(value = "delete from TOUR_INFO where user_id=:userId and id=:id", nativeQuery = true)
    void deleteTourByIdUserId(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "update TOUR_INFO set " +
            "addr1=:addr1, addr2=:addr2, area_code=:areaCode, sigungu_code=:sigunguCode, " +
            "cat1=:cat1, cat2=:cat2, cat3=:cat3, " +
            "content_id=:contentId, content_type_id=:contentTypeId, " +
            "tel=:tel, title=:title, overview=:overview, modified_time=:modifiedTime " +
            "where id=:id and user_id=:userId", nativeQuery = true)
    void updateTourByUserId(@Param("addr1") String addr1, @Param("addr2") String addr2, @Param("areaCode") int areaCode, @Param("sigunguCode") int sigunguCode,
                            @Param("cat1") String cat1, @Param("cat2") String cat2, @Param("cat3") String cat3,
                            @Param("contentId") int contentId, @Param("contentTypeId") int contentTypeId,
                            @Param("tel") String tel, @Param("title") String title, @Param("overview") String overview, @Param("modifiedTime") Date modifiedTime,
                            @Param("id") int id, @Param("userId") int userId);

}
