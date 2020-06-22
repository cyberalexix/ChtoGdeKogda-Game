package com.game.dto;

import com.game.entity.Role;
import com.game.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    String username;
    String password;
    String matching_password;
    Role role;
    String nameUk;
    String nameEn;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.nameUk = user.getNameUk();
        this.nameEn = user.getNameEn();
    }
}
