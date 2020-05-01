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

import java.util.Date;

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
    public void updateQna(QnaDTO qnaDTO, int userId) {
        try{
            int getUserId = qnaDTO.getUserId();
            if(getUserId == userId){
                String title = qnaDTO.getTitle();
                String content = qnaDTO.getContent();
                Date modifiedTime = new Date();
                int id = qnaDTO.getId();

                qnaRepository.updateQnaByIdUserId(title, content, modifiedTime, userId, id);
            }
        } catch (Exception e){
            logger.error("Qna update : " + e.getMessage());
        }

    }

    @Override
    public void deleteQna(int id, int userId) {
        try{
            QnaDTO qnaDTO = QnaDTO.of(qnaRepository.findById(id).orElse(null));
            if(userId == qnaDTO.getUserId()){
                qnaRepository.deleteById(id);
            }
        } catch (Exception e) {
            logger.error("Qna delete : " + e.getMessage());
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
        if (qnaId == qnaCommentDTO.getQnaId()){
            return commentSave(qnaCommentDTO);
        }
        return null;
    }

    @Override
    public void deleteQnaComment(int id, int userId, int qnaId) {
        try{
            QnaCommentDTO qnaCommentDTO = QnaCommentDTO.of(qnaCommentRepository.findById(id).orElse(null));
            if(qnaCommentDTO.getUserId() == userId && qnaCommentDTO.getQnaId() == qnaId){
                qnaCommentRepository.deleteById(id);
            }
        } catch (Exception e){
            logger.error("Qna Comment delete : " + e.getMessage());
        }
    }

    @Override
    public void updateQnaComment(QnaCommentDTO qnaCommentDTO, int id, int userId, int qnaId) {
        try{
            if(userId == qnaCommentDTO.getUserId() && qnaCommentDTO.getQnaId() == qnaId && qnaCommentDTO.getId() == id){
                String comment = qnaCommentDTO.getComment();
                Date modifiedTime = new Date();
                qnaCommentRepository.updateQnaCommentByIdUserId(comment, modifiedTime, id, userId, qnaId);
            }
        } catch (Exception e){
            logger.error("Qna Comment update : " + e.getMessage());
        }

    }
}
