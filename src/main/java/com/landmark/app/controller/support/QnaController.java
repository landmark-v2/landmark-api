package com.landmark.app.controller.support;

import com.landmark.app.model.domain.support.Qna;
import com.landmark.app.model.dto.commnet.QnaCommentDTO;
import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.model.repository.UserRepository;
import com.landmark.app.model.repository.support.QnaRepository;
import com.landmark.app.service.support.QnaService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.AccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.landmark.app.utils.constants.Constants.QNA_API;

@RestController
@RequestMapping(value = QNA_API)
public class QnaController extends LoggerUtils {
    private QnaService qnaService;
    private AccountHelper accountHelper;

    @Autowired
    public QnaController(QnaService qnaService, AccountHelper accountHelper) {
        this.qnaService = qnaService;
        this.accountHelper = accountHelper;
    }

    /** QnA */
    /** Qna 전체 조회 */
    @GetMapping(value = "/search")
    public ResponseEntity<?> getAllQna(HttpServletRequest request){
        try{
            return new ResponseEntity<>(qnaService.findAllQnas(), HttpStatus.OK);
        } catch (Exception e){
            logger.error("Qna(getAllQnas) Controller Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Qna 등록 */
    @PostMapping
    public ResponseEntity<?> createQna(@RequestBody QnaDTO qnaDTO, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            qnaDTO.setUser(user);
            return new ResponseEntity<>(qnaService.createQna(qnaDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(createQna) Controller Error : " +e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Qna 수정 */
    @PutMapping
    public ResponseEntity<?> updateQna(@RequestBody QnaDTO.UpdateQnaDTO updateQnaDTO, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(qnaService.updateQna(updateQnaDTO),HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(updateQna) Controller Error : " +e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Qna 삭제 */
    @DeleteMapping(value = "/{qnaId}")
    public ResponseEntity<?> deleteQna(@PathVariable("qnaId") int qnaId, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            String role = user.getRole().getRolename();
            int userId = accountHelper.getAccountId(request);

            return new ResponseEntity<>(qnaService.deleteQna(userId, role, qnaId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(deleteQna) Controller Error : " +e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // search
    @GetMapping(value = "/keyword")
    public ResponseEntity<?> searchQna(@RequestBody QnaDTO.SearchQna searchQna, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(qnaService.searchQna(searchQna), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(keyword_search) Controller Error : " +e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * QNA 댓글
     */

    /** 댓글 가져오기 */
    @GetMapping(value = "/comment/{qnaId}")
    public ResponseEntity<?> findAllQnaComments(@PathVariable("qnaId") int qnaId, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(qnaService.findAllQnaComments(qnaId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(findAllQnaComments) Controller Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** 댓글 등록 */
    @PostMapping(value = "/comment/{qnaId}")
    public ResponseEntity<?> registerQnaComment(@PathVariable("qnaId") int qnaId, @RequestBody QnaCommentDTO commentDTO, HttpServletRequest request) {
        try {
            commentDTO.setUserId(accountHelper.getAccountId(request));
            return new ResponseEntity<>(qnaService.registerQnaComment(commentDTO, qnaId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(findAllQnas) Controller Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** 댓글 삭제 */
    @DeleteMapping(value = "/comment/{id}")
    public ResponseEntity<?> deleteQnaComment(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            String role = accountHelper.getRole(request);
            int userId = accountHelper.getAccountId(request);
            return new ResponseEntity<>(qnaService.deleteQnaComment(id, userId, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(deleteQnaComment) Controller Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** 댓글 수정 */
    @PutMapping(value = "/comment")
    public ResponseEntity<?> updateQnaComment(@RequestBody QnaCommentDTO commentDTO, HttpServletRequest request) {
        try {
            int userId = accountHelper.getAccountId(request);
            return new ResponseEntity<>(qnaService.updateQnaComment(commentDTO, userId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Qna(updateQnaComment) Controller Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
