package com.landmark.app.service.support;

import com.landmark.app.model.dto.support.NoticeDTO;

public interface NoticeService {
    /**
     * 공지 등록
     */
    NoticeDTO registerNotice(NoticeDTO noticeDTO);

    /**
     * 공지 수정
     */
    NoticeDTO updateNotice(NoticeDTO noticeDTO, String role);

    /**
     * 공지 삭제
     */
    boolean deleteNotice(int id, String role);

}
