package com.landmark.app.model.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.landmark.app.model.domain.user.User;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.constants.Constants;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserDTO {

    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    @JsonIgnore
    private RoleDTO role;

    public static UserDTO of(User user) {
        return MapperUtils.convert(user, UserDTO.class);
    }

    public UsernamePasswordAuthenticationToken toAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password, getAuthorities());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> Constants.ROLE_USER);
    }

}
