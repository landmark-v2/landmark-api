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

    /** SearchService **/
    List<TourInfo> findAllByAreaCodeAndContentTypeIdOrderByCreatedTimeDesc(int areaCode, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, int contentTypeId, String title);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndContentTypeIdOrderByCreatedTimeDesc(int areaCode, int sigunguCode, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, int sigunguCode, int contentTypeId, String title);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeIdOrderByCreatedTimeDesc(int areaCode, int sigunguCode, String cat1, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, int sigunguCode, String cat1, int contentTypeId, String title);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeIdOrderByCreatedTimeDesc(int areaCode, int sigunguCode, String cat1, String cat2, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, int sigunguCode, String cat1, String cat2, int contentTypeId, String title);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeIdOrderByCreatedTimeDesc(int areaCode, int sigunguCode, String cat1, String cat2, String cat3, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndSigunguCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, int sigunguCode, String cat1, String cat2, String cat3, int contentTypeId, String title);
    List<TourInfo> findAllByAreaCodeAndCat1AndContentTypeIdOrderByCreatedTimeDesc(int areaCode, String cat1, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndCat1AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, String cat1, int contentTypeId, String title);
    List<TourInfo> findAllByAreaCodeAndCat1AndCat2AndContentTypeIdOrderByCreatedTimeDesc(int areaCode, String cat1, String cat2, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndCat1AndCat2AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, String cat1, String cat2, int contentTypeId, String title);
    List<TourInfo> findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeIdOrderByCreatedTimeDesc(int areaCode, String cat1, String cat2, String cat3, int contentTypeId);
    List<TourInfo> findAllByAreaCodeAndCat1AndCat2AndCat3AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int areaCode, String cat1, String cat2, String cat3, int contentTypeId, String title);
    List<TourInfo> findAllByCat1AndContentTypeIdOrderByCreatedTimeDesc(String cat1, int contentTypeId);
    List<TourInfo> findAllByCat1AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(String cat1, int contentTypeId, String title);
    List<TourInfo> findAllByCat1AndCat2AndContentTypeIdOrderByCreatedTimeDesc(String cat1, String cat2, int contentTypeId);
    List<TourInfo> findAllByCat1AndCat2AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(String cat1, String cat2, int contentTypeId, String title);
    List<TourInfo> findAllByCat1AndCat2AndCat3AndContentTypeIdOrderByCreatedTimeDesc(String cat1, String cat2, String cat3, int contentTypeId);
    List<TourInfo> findAllByCat1AndCat2AndCat3AndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(String cat1, String cat2, String cat3, int contentTypeId, String title);
    List<TourInfo> findAllByAndContentTypeIdAndTitleContainingOrderByCreatedTimeDesc(int contentTypeId, String title);
    List<TourInfo> findAllByContentTypeIdOrderByCreatedTimeDesc(int contentTypeId);

    TourInfo findById(int id);

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
