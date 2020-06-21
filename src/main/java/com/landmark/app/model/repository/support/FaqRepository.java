package com.landmark.app.model.repository.support;

import com.landmark.app.model.domain.support.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FaqRepository extends JpaRepository<Faq, Integer> {

    @Modifying
    @Transactional
    void deleteById(int id);

    List<Faq> findAll();
    Faq findById(int id);
}
