package com.landmark.app.model.dto.user;

import com.landmark.app.model.domain.user.Role;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;

@Data
public class RoleDTO {

    private int id;
    private String rolename;

    public static RoleDTO of(Role role) {
        return MapperUtils.convert(role, RoleDTO.class);
    }

}
