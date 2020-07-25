package com.landmark.app.model.repository;

import com.landmark.app.model.domain.SigunguCode;
import com.landmark.app.model.domain.SigunguCodePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SigunguCodeRepository extends JpaRepository<SigunguCode, SigunguCodePK> {

    SigunguCode findByAreaCodeAndCode(int areaCode, int code);

}
