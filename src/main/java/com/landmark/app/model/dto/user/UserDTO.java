package com.landmark.app.model.dto.user;

import com.landmark.app.model.domain.user.User;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;

@Data
public class UserDTO {

    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private RoleDTO role;

    public static UserDTO of(User user) {
        return MapperUtils.convert(user, UserDTO.class);
    }
}
