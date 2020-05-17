package com.landmark.app.service.user;

import com.landmark.app.model.domain.user.User;
import com.landmark.app.model.dto.user.RoleDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.model.repository.UserRepository;
import com.landmark.app.service.RedisService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.MailUtils;
import com.landmark.app.utils.constants.Constants;
import com.landmark.app.utils.helper.StaticHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.landmark.app.utils.constants.Constants.*;

@Service
public class UserServiceImpl extends LoggerUtils implements UserService {

    private UserRepository userRepository;
    private RedisService redisService;
    private MailUtils mailUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RedisService redisService, MailUtils mailUtils) {
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.mailUtils = mailUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(username);
        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }

    @Override
    public UserDTO findByUsername(String username) throws Exception {
        try {
            return UserDTO.of(userRepository.findByUsername(username).get());
        } catch (Exception e) {
            logger.error("findByUsername : " + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public UserDTO save(UserDTO userDTO) throws Exception {
        try {
            User user = User.of(toUserDTO(userDTO));
            return UserDTO.of(userRepository.saveAndFlush(user));
        } catch (Exception e) {
            logger.error("save : " + e.getMessage());
            throw new Exception(e);
        }
    }

    private UserDTO toUserDTO(UserDTO userDTO) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRolename(ROLE_USER);
        userDTO.setRole(roleDTO);
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        return userDTO;
    }

    @Override
    public int findIdByUsername(String username) throws Exception {
        try {
            User user = userRepository.findByUsername(username).get();
            return user.getId();
        } catch (Exception e) {
            logger.error("findIdByUsername : " + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public UserDTO findById(int id) throws Exception {
        try {
            User user = userRepository.findById(id).get();
            return UserDTO.of(user);
        } catch (Exception e) {
            logger.error("findById : " + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public boolean sendCertNum(String email) throws Exception {
        try {
            int certNum = StaticHelper.getCertNum();
            String title = "[랜드마크] 이메일 인증번호 입니다.";
            String content = "인증번호 : " + certNum + " \n\n인증번호를 3분 이내에 입력해주세요 :) \n\n";

            if (redisService.save(email, certNum + "")) {
                redisService.expire(email, 3, TimeUnit.MINUTES);
                return mailUtils.sendMail(email, title, content);
            }
        } catch (Exception e) {
            logger.error("sendCertNum : " + e.getMessage());
            throw new Exception(e);
        }

        return false;
    }

    @Override
    public boolean checkCertNum(String email, int certNum) throws Exception {
        try {
            String savedCertNum = redisService.get(email);

            if (savedCertNum.equals(certNum + "")) {
                return true;
            }
        } catch (Exception e) {
            logger.error("checkCertNum : " + e.getMessage());
            throw new Exception(e);
        }

        return false;
    }

    @Override
    public boolean checkUsername(String username) throws Exception {
        try {
            userRepository.findByUsername(username).isPresent();
        } catch (Exception e) {
            logger.error("checkCertNum : " + e.getMessage());
            throw new Exception(e);
        }

        return false;
    }

    @Override
    public boolean checkEmail(String email) throws Exception {
        try {
            userRepository.findByEmail(email).isPresent();
        } catch (Exception e) {
            logger.error("checkEmail : " + e.getMessage());
            throw new Exception(e);
        }

        return false;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, UserDTO.UpdateUserDTO updateUserDTO) throws Exception {
        try {
            if (userDTO != null) {
                String account = userDTO.getUsername();

                if (StringUtils.isEmpty(updateUserDTO.getName()) && !userDTO.getName().equals(updateUserDTO.getName())) {
                    userDTO.setName(updateUserDTO.getName());
                }

                if (StringUtils.isEmpty(updateUserDTO.getPassword())) {
                    userDTO.setPassword(updateUserDTO.getPassword());
                }

                if (StringUtils.isEmpty(updateUserDTO.getEmail())) {
                    // 이메일 변경 시 인증번호를 발급 받아서 인증번호 유효성 체크 후 변경한다.
                    if (checkCertNum(updateUserDTO.getEmail(), updateUserDTO.getCertNum())) {
                        userDTO.setEmail(updateUserDTO.getEmail());
                    } else {
                        logger.error("Update User (" + account + ") - Email Cert Number Invalid : " + updateUserDTO.getEmail());
                    }
                }

                userDTO = save(userDTO);
            }
        } catch (Exception e) {
            logger.error("updateUser : " + e.getMessage());
            throw new Exception(e);
        }

        return userDTO;
    }

    @Override
    public boolean deleteUser(int id, UserDTO userDTO) throws Exception {
        try {
            // 개발자는 모든 사용자 강퇴 가능
            if (userDTO.getRole().getRolename().equals(ROLE_DEV)) {
                // 1. qna, 리뷰, 여행지 정보 등 삭제

                // 2. 사용자 삭제
                userRepository.deleteById(id);
                logger.info("Developer -> Delete User Index : " + id);
                return true;
            }
            // 그외에는 본인만 회원탈퇴
            else {
                // 1. qna, 리뷰, 여행지 정보 등 삭제

                // 2. 사용자 삭제
                userRepository.deleteById(id);
                logger.info("Delete User Index : " + id);
            }

            return true;
        } catch (Exception e) {
            logger.error("deleteUser : " + e.getMessage());
            throw new Exception(e);
        }
    }

}
