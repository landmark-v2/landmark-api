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

    /** QnA 등록 */
    QnaDTO createQna(QnaDTO qnaDTO);

    /** QnA 수정 */
    QnaDTO updateQna(QnaDTO.UpdateQnaDTO updateQnaDTO);

    /** QnA 삭제 */
    boolean deleteQna(int userId, String role, int qnaId);


    // search
    /** 검색 */
    List<QnaDTO> searchQna(QnaDTO.SearchQna searchQna);



    /** QnA Comment 가져오기 */
    List<QnaCommentDTO> findAllQnaComments(int qnaId);

    /** QNA 댓글 등록 */
    QnaCommentDTO registerQnaComment(QnaCommentDTO qnaCommentDTO, int qnaId);

    /** QNA 댓글 삭제 */
    boolean deleteQnaComment(int id, int userId, String role);

    /** QNA 댓글 수정 */
    QnaCommentDTO updateQnaComment(QnaCommentDTO qnaCommentDTO, int userId, int id);

    /** 회원탈퇴 시 QnA, 댓글 있으면 삭제 */
    boolean deleteByUserId(int userId);
}


