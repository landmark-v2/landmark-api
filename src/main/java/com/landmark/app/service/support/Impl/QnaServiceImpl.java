package com.landmark.app.service.support.Impl;

import com.landmark.app.model.domain.support.Qna;
import com.landmark.app.model.domain.comment.QnaComment;
import com.landmark.app.model.domain.user.User;
import com.landmark.app.model.dto.commnet.QnaCommentDTO;
import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.model.repository.UserRepository;
import com.landmark.app.model.repository.support.QnaCommentRepository;
import com.landmark.app.model.repository.support.QnaRepository;
import com.landmark.app.service.support.QnaService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.StaticHelper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

import static com.landmark.app.utils.constants.Constants.ROLE_ADMIN;
import static com.landmark.app.utils.constants.Constants.ROLE_DEV;

@Service
public class QnaServiceImpl extends LoggerUtils implements QnaService {

    private QnaRepository qnaRepository;
    private QnaCommentRepository qnaCommentRepository;
    private UserRepository userRepository;

    public QnaServiceImpl(QnaRepository qnaRepository, QnaCommentRepository qnaCommentRepository, UserRepository userRepository) {
        this.qnaRepository = qnaRepository;
        this.qnaCommentRepository = qnaCommentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<QnaDTO> findAllQnas() {
        try {
            List<Qna> qnas = qnaRepository.findAll();
            return QnaDTO.of(qnas);
        } catch(Exception e) {
            logger.error("find all Qna Error : " + e.getMessage());
            return null;
        }
    }

    @Override
    public QnaDTO createQna(QnaDTO qnaDTO) {
        try {
            Qna qna = new Qna();
            qna.setUser(User.of(qnaDTO.getUser()));
            qna.setTitle(qnaDTO.getTitle());
            qna.setContent(qnaDTO.getContent());
            qna.setCreatedTime(new Date());
            qna.setModifiedTime(new Date());

            return QnaDTO.of(qnaRepository.save(qna));
        } catch (Exception e) {
            logger.error("create Qna Error : " + e.getMessage());
            return null;
        }
    }

    @Override
    public QnaDTO updateQna(QnaDTO.UpdateQnaDTO updateQnaDTO) {
        try {
            Qna qna = qnaRepository.findById(updateQnaDTO.getId());

            if(!StringUtils.isEmpty(updateQnaDTO.getTitle())) {
                qna.setTitle(updateQnaDTO.getTitle());
            }

            if(!StringUtils.isEmpty(updateQnaDTO.getContent())) {
                qna.setContent(updateQnaDTO.getContent());
            }

            qna.setModifiedTime(new Date());

            return QnaDTO.of(qnaRepository.save(qna));
        } catch (Exception e) {
            logger.error("update QnA Error : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteQna(int userId, String role, int qnaId) {
        try {
            QnaDTO qnaDTO = QnaDTO.of(qnaRepository.findById(qnaId));
            if (qnaDTO.getUser().getId() == userId || role.equalsIgnoreCase(ROLE_ADMIN)) {
                qnaRepository.deleteById(qnaId);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("delete QnA Error : " + e.getMessage());
            return false;
        }
    }


    /**
     * Search
     */

    @Override
    public List<QnaDTO> searchQna(QnaDTO.SearchQna searchQna) {
        try {
            String type = searchQna.getType();
            String search = searchQna.getSearch();

            if("name".equals(type)) {
                int userId = userRepository.findByUsername(search).get().getId();
                return QnaDTO.of(qnaRepository.findAllByUserId(userId));
            } else if ("title".equals(type)) {
                return QnaDTO.of(qnaRepository.findByTitleContaining(search));
            }

            return null;
        } catch (Exception e) {
            logger.error("QnA search By UserId : " + e.getMessage());
            return null;
        }
    }


    /**
     * QnA 댓글
     */

    @Override
    public List<QnaCommentDTO> findAllQnaComments(int qnaId) {
        try {
            return QnaCommentDTO.of(qnaCommentRepository.findAllByQnaId(qnaId));
        } catch (Exception e) {
            logger.error("find All QnA Comment : " + e.getMessage());
            return null;
        }
    }

    @Override
    public QnaCommentDTO registerQnaComment(QnaCommentDTO qnaCommentDTO, int qnaId){
        try {
            QnaComment comment = new QnaComment();
            comment.setUserId(qnaCommentDTO.getUserId());
            comment.setQnaId(qnaId);
            comment.setComment(qnaCommentDTO.getComment());
            comment.setCreatedTime(new Date());
            comment.setModifiedTime(new Date());
            return QnaCommentDTO.of(qnaCommentRepository.save(comment));
        } catch (Exception e) {
            logger.error("register Qna Comment : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteQnaComment(int id, int userId, String role) {
        try {
            if(ROLE_DEV.equals(role)) {
                qnaCommentRepository.deleteById(id);
                return true;
            }

            QnaCommentDTO commentDTO = QnaCommentDTO.of(qnaCommentRepository.findById(id));
            if(commentDTO.getUserId() == userId) {
                qnaCommentRepository.deleteById(id);
                return true;
            }

            return false;
        } catch (Exception e) {
            logger.error("delete Qna Comment : " + e.getMessage());
            return false;
        }
    }

    @Override
    public QnaCommentDTO updateQnaComment(QnaCommentDTO qnaCommentDTO, int userId) {
        try {
            if(qnaCommentDTO.getUserId() == userId) {
                QnaComment comment = qnaCommentRepository.findById(qnaCommentDTO.getId());

                if(!StringUtils.isEmpty(qnaCommentDTO.getComment())) {
                    comment.setComment(qnaCommentDTO.getComment());
                }

                comment.setModifiedTime(new Date());
                return QnaCommentDTO.of(qnaCommentRepository.save(comment));
            }
            return null;
        } catch (Exception e) {
            logger.error("update Qna Comment : " + e.getMessage());
            return null;
        }
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
