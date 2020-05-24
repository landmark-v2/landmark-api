package com.landmark.app.model.repository;

import com.landmark.app.model.domain.AreaCodeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AreaCodeCountRepository extends JpaRepository<AreaCodeCount, Integer> {

    @Modifying
    @Transactional
    @Query(value = "select a.code as area_code, if(t.user_id=:userId, count(*), 0) as level, a.name from AREA_CODE a left join TOUR_REVIEW t on a.code=t.area_code group by a.code", nativeQuery = true)
    List<AreaCodeCount> countAllByUserIdGroupByAreaCode(@Param("userId") int userId);

}
