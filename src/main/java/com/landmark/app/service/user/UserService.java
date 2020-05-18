package com.landmark.app.service.user;

import com.landmark.app.model.dto.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO findByUsername(String username) throws Exception;

    // 회원가입
    UserDTO save(UserDTO userDTO) throws Exception;

    int findIdByUsername(String username) throws Exception;

    // 회원정보 조회
    UserDTO findById(int id) throws Exception;

    // 인증번호 발송
    boolean sendCertNum(String email) throws Exception;

    // 인증번호 인증
    boolean checkCertNum(String email, int certNum) throws Exception;

    // 아이디 중복 체크
    boolean checkUsername(String username) throws Exception;

    // 이메일 중복 체크
    boolean checkEmail(String email) throws Exception;

    // 회원정보 수정
    UserDTO updateUser(UserDTO userDTO, UserDTO.UpdateUserDTO updateUserDTO) throws Exception;

    // 회원탈퇴
    boolean deleteUser(int id, String role) throws Exception;

}
