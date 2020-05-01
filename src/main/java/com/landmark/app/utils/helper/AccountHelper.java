package com.landmark.app.utils.helper;

import com.landmark.app.model.dto.user.UserDTO;
import com.landmark.app.security.authentication.TokenAuthentication;
import com.landmark.app.service.user.UserService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccountHelper extends LoggerUtils {

    private UserService userService;
    private TokenAuthentication tokenAuthentication;

    @Autowired
    public AccountHelper(UserService userService, TokenAuthentication tokenAuthentication) {
        this.userService = userService;
        this.tokenAuthentication = tokenAuthentication;
    }

    public UserDTO getAccountInfo(HttpServletRequest request) {
        try {
            String username = tokenAuthentication.generateAuthenticationFromRequest(request).getName();
            return userService.findByUsername(username);
        } catch (Exception e) {
            logger.error("getAccountInfo : " + e.getMessage());
            return null;
        }
    }

    public int getAccountId(HttpServletRequest request) {
        try {
            String username = tokenAuthentication.generateAuthenticationFromRequest(request).getName();
            return userService.findByUsername(username).getId();
        } catch (Exception e) {
            logger.error("getAccountId : " + e.getMessage());
            return 0;
        }
    }

}
