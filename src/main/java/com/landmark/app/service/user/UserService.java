package com.landmark.app.service.user;

import com.landmark.app.model.dto.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO findByUsername(String username) throws Exception;

    UserDTO register(UserDTO userDTO) throws Exception;

    int findIdByUsername(String username) throws Exception;

}
