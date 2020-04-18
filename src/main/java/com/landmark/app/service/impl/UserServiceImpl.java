package com.landmark.app.service.impl;

import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.model.repository.UserRepository;
import com.landmark.app.service.UserService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends LoggerUtils implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int findIdByUserName(String account) {
        try {
            UserDTO userDTO = UserDTO.of(userRepository.findByUserName(account));

            if (userDTO != null) {
                return userDTO.getId();
            }
        } catch (Exception e) {
            logger.error("findIdByUserName : " + e.getMessage());
        }

        return 0;
    }
}
