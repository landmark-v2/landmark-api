package com.landmark.app.service.user;

import com.landmark.app.model.domain.user.User;
import com.landmark.app.model.dto.user.RoleDTO;
import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.model.repository.UserRepository;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.landmark.app.utils.constants.Constants.*;

@Service
public class UserServiceImpl extends LoggerUtils implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    private UserDTO toUserDTO(UserDTO userDTO) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRolename(ROLE_USER);
        userDTO.setRole(roleDTO);
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        return userDTO;
    }
}
