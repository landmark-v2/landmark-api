package com.landmark.app.service.support;

import com.landmark.app.model.dto.commnet.QnaCommentDTO;
import com.landmark.app.model.dto.support.QnaDTO;

import java.util.List;

public interface QnaService {
    /**
     * 구현해야 할 거
     *
     * QnA 키워드 검색
     *      전체 글 가져오기
     *      특정 글 가져오기
     *      등록/수정/삭제
     */

    /** QnA 가져오기 */
    List<QnaDTO> findAllQnas();

    QnaDTO createQna(QnaDTO qnaDTO);

    /** QnA Comment 가져오기 */
    List<QnaCommentDTO> getAllQnaComments(int qnaId);

    /** QNA 댓글 등록 */
    QnaCommentDTO registerQnaComment(QnaCommentDTO qnaCommentDTO, int qnaId);

    /** QNA 댓글 삭제 */
    boolean deleteQnaComment(int id, int userId, int qnaId, String role);

    /** QNA 댓글 수정 */
    QnaCommentDTO updateQnaComment(QnaCommentDTO qnaCommentDTO, int userId);

    /** 회원탈퇴 시 QnA, 댓글 있으면 삭제 */
    boolean deleteByUserId(int userId);
}


