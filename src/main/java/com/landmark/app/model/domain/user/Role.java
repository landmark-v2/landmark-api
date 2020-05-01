package com.landmark.app.model.domain.user;

import com.landmark.app.model.dto.user.RoleDTO;
import com.landmark.app.utils.MapperUtils;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "ROLE")
@ToString(exclude = "userRole")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String rolename;

    @OneToMany
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<User> userRole;

    public static Role of(RoleDTO roleDTO) {
        return MapperUtils.convert(roleDTO, Role.class);
    }

}