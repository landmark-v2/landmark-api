package com.landmark.app.service.user;

import com.landmark.app.model.domain.user.User;
import com.landmark.app.model.dto.user.RoleDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.model.repository.UserRepository;
import com.landmark.app.service.RedisService;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.MailUtils;
import com.landmark.app.utils.helper.StaticHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDTO register(UserDTO userDTO) throws Exception {
        try {
            User user = User.of(toUserDTO(userDTO));
            return UserDTO.of(userRepository.saveAndFlush(user));
        } catch (Exception e) {
            logger.error("register : " + e.getMessage());
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
        return false;
    }

    @Override
    public boolean checkEmail(String email) throws Exception {
        return false;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws Exception {
        return null;
    }

    @Override
    public boolean deleteUser(int id) throws Exception {
        return false;
    }

}
