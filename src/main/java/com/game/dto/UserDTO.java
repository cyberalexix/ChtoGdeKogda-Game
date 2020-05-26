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
    Role role;
    String name_uk;
    String name_en;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.name_uk = user.getName_uk();
        this.name_en = user.getName_en();
    }
}
