package com.landmark.app.controller.support;

import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.model.repository.UserRepository;
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

    @Autowired
    public QnaController(QnaService qnaService, AccountHelper accountHelper, UserRepository userRepository) {
        this.qnaService = qnaService;
        this.accountHelper = accountHelper;
        this.userRepository = userRepository;
    }

    /** QnA */
    /** Qna 전체 조회 */
    @GetMapping(value = "/search")
    public ResponseEntity<?> getAllQna(HttpServletRequest request){
        try{
            return new ResponseEntity<>(qnaService.findAllQnas(), HttpStatus.OK);
        } catch (Exception e){
            logger.error("getAllQnas : " + e.getMessage());
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
            logger.error("createQna Controller Error : " +e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
