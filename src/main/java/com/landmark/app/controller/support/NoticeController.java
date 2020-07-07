package com.landmark.app.controller.support;

import com.landmark.app.model.dto.support.FaqDTO;
import com.landmark.app.model.dto.support.NoticeDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.service.support.NoticeService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.AccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.landmark.app.utils.constants.Constants.NOTICE_API;

@RestController
@RequestMapping(value = NOTICE_API)
public class NoticeController extends LoggerUtils {

    private NoticeService noticeService;
    private AccountHelper accountHelper;

    @Autowired
    public NoticeController(NoticeService noticeService, AccountHelper accountHelper){
        this.noticeService = noticeService;
        this.accountHelper = accountHelper;
    }

    @PostMapping
    public ResponseEntity<?> registerNotice(@RequestParam NoticeDTO noticeDTO, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(noticeService.registerNotice(noticeDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("registerNotice : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateNotice(@RequestParam NoticeDTO noticeDTO, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            String role = user.getRole().getRolename();
            return new ResponseEntity<>(noticeService.updateNotice(noticeDTO, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateNotice : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{noticeId}")
    public ResponseEntity<?> deleteFaq(@PathVariable("noticeId") int noticeId, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            String role = user.getRole().getRolename();

            return new ResponseEntity<>(noticeService.deleteNotice(noticeId, role), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("deleteNotice : " + e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
