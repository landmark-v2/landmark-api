package com.landmark.app.service.support;

import com.landmark.app.model.dto.support.QnaCommentDTO;
import com.landmark.app.model.dto.support.QnaDTO;

import java.util.List;

public interface QnaService {
    /** QnA 키워드 검색 */
    List<QnaDTO> getQnaByKeyword(String s);

    /** QnA 전체 목록 가져오기 */
    List<QnaDTO> getAllQnas();

    /** QnA 글 가져오기*/
    QnaDTO getQna(int qnaId);

    /** QNA 등록 */
    QnaDTO registerQna(QnaDTO qnaDTO);

    /** QNA 수정 */
    QnaDTO updateQna(QnaDTO qnaDTO, int userId);

    /** QNA 삭제 */
    boolean deleteQna(int id, int userId, String role);



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


