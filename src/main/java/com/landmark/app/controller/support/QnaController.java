package com.landmark.app.controller.support;

import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.service.support.QnaService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.AccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
            return new ResponseEntity<>(qnaService.getAllQnas(), HttpStatus.OK);
        } catch (Exception e){
            logger.error("getAllQnas : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Qna 키워드 조회 */
    @PostMapping(value = "/search/key")
    public ResponseEntity<?> getQnaByKeyword(@RequestBody String s, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(qnaService.getQnaByKeyword(s), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getAllQnas : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Qna 게시글 불러오기 */
    @GetMapping(value = "/{qnaId}")
    public ResponseEntity<?> getQna(@PathVariable("qnaId") int qnaId, HttpServletRequest request){
        try{
            return new ResponseEntity<>(qnaService.getQna(qnaId), HttpStatus.OK);
        } catch (Exception e){
            logger.error("get Qna : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // QnA 등록
    @PostMapping
    public ResponseEntity<?> registerQna(@RequestBody QnaDTO qnaDTO, HttpServletRequest request) {
        try {
            int userId = accountHelper.getAccountId(request);
            qnaDTO.setUserId(userId);
            return new ResponseEntity<>(qnaService.registerQna(qnaDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("registerQnA : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // QnA 수정
    @PutMapping
    public ResponseEntity<?> updateQna(@RequestBody QnaDTO qnaDTO, HttpServletRequest request) {
        try {
            int userId = accountHelper.getAccountId(request);
            return new ResponseEntity<>(qnaService.updateQna(qnaDTO, userId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateQnA : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // QnA 삭제
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteQna(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            String role = user.getRole().getRolename();
            int userId = accountHelper.getAccountId(request);

            return new ResponseEntity<>(qnaService.deleteQna(id, userId, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateQna : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
