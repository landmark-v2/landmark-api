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
    void updateNotice(NoticeDTO noticeDTO);

    /**
     * 공지 삭제
     */
    void deleteNotice(int id);

}
