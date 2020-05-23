package com.landmark.app.service.support;

import com.landmark.app.model.dto.support.QnaCommentDTO;
import com.landmark.app.model.dto.support.QnaDTO;

public interface QnaService {
    /**
     * QNA 등록
     */
    QnaDTO registerQna(QnaDTO qnaDTO);

    /**
     * QNA 수정
     */
    void updateQna(QnaDTO qnaDTO, int userId);

    /**
     * QNA 삭제
     */
    void deleteQna(int id, int userId);

    /**
     * QNA 댓글 등록
     */
    QnaCommentDTO registerQnaComment(QnaCommentDTO qnaCommentDTO, int qnaId);

    /**
     * QNA 댓글 삭제
     */
    void deleteQnaComment(int id, int userId, int qnaId);

    /**
     * QNA 댓글 수정
     */
    void updateQnaComment(QnaCommentDTO qnaCommentDTO, int userId);

    /**
     * 회원탈퇴 시 QnA, 댓글 있으면 삭제
     */
    boolean deleteByUserId(int userId);

}


