package com.landmark.app.model.repository;

import com.landmark.app.model.domain.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentTypeRepository extends JpaRepository<ContentType, Integer> {
}
