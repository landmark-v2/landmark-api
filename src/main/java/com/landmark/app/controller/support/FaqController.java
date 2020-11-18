package com.landmark.app.controller.support;

import com.landmark.app.model.dto.support.FaqDTO;
import com.landmark.app.model.dto.support.QnaDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.service.support.FaqService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.AccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.landmark.app.utils.constants.Constants.FAQ_API;

@RestController
@RequestMapping(value = FAQ_API)
public class FaqController extends LoggerUtils {

    private FaqService faqService;
    private AccountHelper accountHelper;

    @Autowired
    public FaqController(FaqService faqService, AccountHelper accountHelper){
        this.faqService = faqService;
        this.accountHelper = accountHelper;
    }

    /** FAQ 전체 조회 */
    @GetMapping
    public ResponseEntity<?> getAllFaq(HttpServletRequest request){
        try{
            return new ResponseEntity<>(faqService.getAllFaq(), HttpStatus.OK);
        } catch (Exception e){
            logger.error("getAllFaqs : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** FAQ 글 가져오기 */
    @GetMapping(value = "/{faqId}")
    public ResponseEntity<?> getFaq(@PathVariable("faqId") int faqId , HttpServletRequest request){
        try{
            return new ResponseEntity<>(faqService.getFaq(faqId), HttpStatus.OK);
        } catch (Exception e){
            logger.error("getFaq : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** FAQ 등록 */
    @PostMapping
    public ResponseEntity<?> registerFaq(@RequestBody FaqDTO faqDTO, HttpServletRequest request) {
        try {
            String role = accountHelper.getRole(request);
            return new ResponseEntity<>(faqService.registerFaq(faqDTO, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("registerFAQ : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** FAQ 수정 */
    @PutMapping
    public ResponseEntity<?> updateFaq(@RequestBody FaqDTO.UpdateFaqDTO faqDTO, HttpServletRequest request) {
        try {
            String role = accountHelper.getRole(request);
            return new ResponseEntity<>(faqService.updateFaq(faqDTO, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateFAQ : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** FAQ 삭제 */
    @DeleteMapping(value = "/{faqId}")
    public ResponseEntity<?> deleteFaq(@PathVariable("faqId") int faqId, HttpServletRequest request) {
        try {
            String role = accountHelper.getRole(request);
            return new ResponseEntity<>(faqService.deleteFaq(faqId, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("deleteFAQ : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
