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
    FaqDTO updateFaq(FaqDTO faqDTO, String role);

    /**
     * FAQ 삭제
     */
    boolean deleteFaq(int faqId, String role);
}
