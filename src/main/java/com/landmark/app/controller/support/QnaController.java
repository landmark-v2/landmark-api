package com.landmark.app.controller.support;

import com.landmark.app.model.domain.support.Qna;
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

import static com.landmark.app.utils.constants.Constants.QNA_API;

@RestController
@RequestMapping(value = QNA_API)
public class QnaController extends LoggerUtils {
    private QnaService qnaService;
    private AccountHelper accountHelper;
    private UserRepository userRepository;
    private QnaRepository qnaRepository;

    @Autowired
    public QnaController(QnaService qnaService, AccountHelper accountHelper, UserRepository userRepository, QnaRepository qnaRepository) {
        this.qnaService = qnaService;
        this.accountHelper = accountHelper;
        this.userRepository = userRepository;
        this.qnaRepository = qnaRepository;
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
}
