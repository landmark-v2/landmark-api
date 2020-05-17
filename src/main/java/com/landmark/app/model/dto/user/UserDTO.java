package com.landmark.app.model.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.landmark.app.model.domain.user.User;
import com.landmark.app.utils.MapperUtils;
import com.landmark.app.utils.constants.Constants;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Data
public class UserDTO {

    private int id;

    @NotNull
    @Length(max = 15)
    private String username;

    @NotNull
    @Length(min = 4, max = 15)
//    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;

    private String name;

    @Email
    private String email;

    @JsonIgnore
    private RoleDTO role;

    @Getter
    public static class UpdateUserDTO {
        private String password;
        private String name;
        private String email;
        private int certNum;
    }

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
