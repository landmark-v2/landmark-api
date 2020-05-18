package com.landmark.app.controller.user;

import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.service.user.UserService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.constants.Constants;
import com.landmark.app.utils.helper.AccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.landmark.app.utils.constants.Constants.USER_API;

@RestController
@RequestMapping(value = USER_API)
public class UserController extends LoggerUtils {

    private UserService userService;
    private AccountHelper accountHelper;

    @Autowired
    public UserController(UserService userService, AccountHelper accountHelper) {
        this.userService = userService;
        this.accountHelper = accountHelper;
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
     * 인증번호 확인
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
    @GetMapping(value = "/check/account")
    public ResponseEntity<?> checkDuplicationAccount(@RequestParam String account) {
        try {
            return new ResponseEntity<>(userService.checkUsername(account), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("checkDuplicationAccount : " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 이메일 중복 체크
     */
    @GetMapping(value = "/check/email")
    public ResponseEntity<?> checkDuplicationEmail(@RequestParam String email) {
        try {
            return new ResponseEntity<>(userService.checkEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("checkDuplicationEmail : " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO user = userService.save(userDTO);
            logger.info("Register User - " + user.toString());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("register : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원정보 조회
     */
    @GetMapping
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getUser : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원정보 수정
     */
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO.UpdateUserDTO updateUser, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);
            return new ResponseEntity<>(userService.updateUser(user, updateUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUser : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 비밀번호 확인
     */
    @GetMapping(value = "/check/pw")
    public ResponseEntity<?> checkPassword(@RequestParam String password, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);

            // 기존의 암호화된 비밀번호와 입력한 비밀번호를 비교한다.
            return new ResponseEntity<>(new BCryptPasswordEncoder().matches(password, user.getPassword()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUser : " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, HttpServletRequest request) {
        try {
            UserDTO user = accountHelper.getAccountInfo(request);

            if (id == user.getId()) {
                String role = user.getRole().getRolename();
                return new ResponseEntity<>(userService.deleteUser(id, role), HttpStatus.OK);
            } else {
                // 탈퇴하려는 인덱스 번호와 로그인 한 사용자의 인덱스 번호가 다르면 false 리턴
                logger.info("Index Invalid : request index - " + id + ", user index - " + user.getId());
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("deleteUser : " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
