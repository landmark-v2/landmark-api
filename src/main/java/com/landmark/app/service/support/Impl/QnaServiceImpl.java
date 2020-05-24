package com.landmark.app.service.support.Impl;

import com.landmark.app.model.domain.support.Qna;
import com.landmark.app.model.domain.support.QnaComment;
import com.landmark.app.model.dto.support.QnaCommentDTO;
import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.model.repository.support.QnaCommentRepository;
import com.landmark.app.model.repository.support.QnaRepository;
import com.landmark.app.service.support.QnaService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

import static com.landmark.app.utils.constants.Constants.ROLE_DEV;

@Service
public class QnaServiceImpl extends LoggerUtils implements QnaService {

    private QnaRepository qnaRepository;
    private QnaCommentRepository qnaCommentRepository;

    public QnaServiceImpl(QnaRepository qnaRepository, QnaCommentRepository qnaCommentRepository) {
        this.qnaRepository = qnaRepository;
        this.qnaCommentRepository = qnaCommentRepository;
    }

    public QnaDTO qnaSave(QnaDTO qnaDTO){
        try{
            qnaDTO.setModifiedTime(new Date());
            Qna qna = qnaRepository.saveAndFlush(Qna.of(qnaDTO));
            return QnaDTO.of(qna);
        } catch (Exception e){
            logger.error("QNA save : " + e.getMessage());
            return null;
        }
    }

    @Override
    public QnaDTO registerQna(QnaDTO qnaDTO) {
        qnaDTO.setCreatedTime(new Date());
        return qnaSave(qnaDTO);
    }

    @Override
    public QnaDTO updateQna(QnaDTO qnaDTO, int userId) {
        try{
            if(qnaDTO.getUserId() == userId){
                qnaDTO.setModifiedTime(new Date());
                return qnaSave(qnaDTO);
            }
        } catch (Exception e){
            logger.error("Qna update : " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteQna(int id, int userId, String role) {
        try{
            QnaDTO qnaDTO = QnaDTO.of(qnaRepository.findById(id).orElse(null));
            if(userId == qnaDTO.getUserId() || role.equalsIgnoreCase(ROLE_DEV)){
                qnaRepository.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            logger.error("Qna delete : " + e.getMessage());
            return false;
        }
    }


    /**
     * QnA 댓글
     */

    public QnaCommentDTO commentSave(QnaCommentDTO qnaCommentDTO){
        try{
            qnaCommentDTO.setModifiedTime(new Date());
            QnaComment qnaComment = qnaCommentRepository.saveAndFlush(QnaComment.of(qnaCommentDTO));
            return QnaCommentDTO.of(qnaComment);
        } catch (Exception e){
            logger.error("Qna Comment save : " + e.getMessage());
            return null;
        }
    }

    @Override
    public QnaCommentDTO registerQnaComment(QnaCommentDTO qnaCommentDTO, int qnaId) {
        qnaCommentDTO.setCreatedTime(new Date());
        qnaCommentDTO.setQnaId(qnaId);
        return commentSave(qnaCommentDTO);
    }

    @Override
    public boolean deleteQnaComment(int id, int userId, int qnaId, String role) {
        try{
            QnaCommentDTO qnaCommentDTO = QnaCommentDTO.of(qnaCommentRepository.findById(id).get());
            if((qnaCommentDTO.getUserId() == userId && qnaCommentDTO.getQnaId() == qnaId ) || role.equalsIgnoreCase(ROLE_DEV)){
                qnaCommentRepository.deleteById(id);
            }
            return true;
        } catch (Exception e){
            logger.error("Qna Comment delete : " + e.getMessage());
            return false;
        }
    }

    @Override
    public QnaCommentDTO updateQnaComment(QnaCommentDTO qnaCommentDTO, int userId) {
        try{
            if(userId == qnaCommentDTO.getUserId()){
                qnaCommentDTO.setModifiedTime(new Date());
                return commentSave(qnaCommentDTO);
            }
        } catch (Exception e){
            logger.error("Qna Comment update : " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteByUserId(int userId) {
        try {
            // 1. 해당 유저의 qna 리스트를 불러온다.
            List<QnaDTO> qnas = QnaDTO.of(qnaRepository.findAllByUserId(userId));

            if (!ObjectUtils.isEmpty(qnas)) {
                for (QnaDTO qna : qnas) {
                    // 2. qna 에 달린 댓글이 있으면 삭제한다.
                    try {
                        qnaCommentRepository.deleteByQnaId(qna.getId());
                    } catch (Exception e) {
                        continue;
                    }

                    // 3. qna 를 삭제한다.
                    qnaRepository.deleteById(qna.getId());
                }
            }

            // qna 가 없거나 삭제 했으면 return true
            return true;
        } catch (Exception e) {
            logger.error("deleteByUserId : " + e.getMessage());
            return false;
        }
    }
}
