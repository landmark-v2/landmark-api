package com.landmark.app.service.support;

import com.landmark.app.model.dto.support.FaqDTO;

public interface FaqService {
    /**
     * FAQ 등록
     */
    FaqDTO registerFaq(FaqDTO faqDTO);

    /**
     * FAQ 수정
     */
    void updateFaq(FaqDTO faqDTO);

    /**
     * FAQ 삭제
     */
    void deleteFaq(int id);
}
