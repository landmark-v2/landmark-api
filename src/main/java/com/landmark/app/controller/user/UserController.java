package com.landmark.app.controller.user;

import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.service.user.UserService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.landmark.app.utils.constants.Constants.USER_API;

@RestController
@RequestMapping(value = USER_API)
public class UserController extends LoggerUtils {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 인증번호 발송
     */
    @PostMapping(value = "/cert")
    public ResponseEntity<?> sendCertNum(@RequestParam String email) {
        try {
            return new ResponseEntity<>(userService.sendCertNum(email), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("sendCertNum : " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 인증번호 인증
     */
    @GetMapping(value = "/cert")
    public ResponseEntity<?> checkCertNum(@RequestParam String email, @RequestParam int certNum) {
        try {
            return new ResponseEntity<>(userService.checkCertNum(email, certNum), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("sendCertNum : " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 아이디 중복 체크
     */

    /**
     * 이메일 중복 체크
     */


    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<>(userService.register(userDTO), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("register : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원정보 조회
     */

    /**
     * 회원정보 수정
     */

    /**
     * 비밀번호 확인
     */

    /**
     * 회원탈퇴
     */

}
