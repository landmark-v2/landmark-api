package com.landmark.app.service.support;

import com.landmark.app.model.dto.support.FaqDTO;

import java.util.List;

public interface FaqService {
    /** FAQ 등록 */
    FaqDTO registerFaq(FaqDTO faqDTO, String role);

    /** FAQ 수정 */
    FaqDTO updateFaq(FaqDTO.UpdateFaqDTO faqDTO, String role);

    /** FAQ 삭제 */
    boolean deleteFaq(int faqId, String role);

    /** faq 전체 조회 */
    List<FaqDTO> getAllFaq();

    /** 특정 faq 가져오기 */
    FaqDTO getFaq(int id);
}
