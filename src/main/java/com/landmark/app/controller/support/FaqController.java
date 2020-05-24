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

    @GetMapping
    public ResponseEntity<?> registerFaq(@Valid @RequestBody FaqDTO faqDTO, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(faqService.registerFaq(faqDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("registerFAQ : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateFaq(@RequestBody FaqDTO faqDTO, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            String role = user.getRole().getRolename();
            return new ResponseEntity<>(faqService.updateFaq(faqDTO, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateFAQ : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{faqId}")
    public ResponseEntity<?> deleteFaq(@PathVariable("faqId") int faqId, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            String role = user.getRole().getRolename();

            return new ResponseEntity<>(faqService.deleteFaq(faqId, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("deleteFAQ : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
