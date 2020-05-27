package com.game.service;

import com.game.config.CryptConfig;
import com.game.config.SecurityConfig;
import com.game.dto.UserDTO;
import com.game.entity.Role;
import com.game.entity.User;
import com.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private CryptConfig cryptConfig;

    @Autowired
    public UserService(UserRepository userRepository, CryptConfig cryptConfig) {
        this.userRepository = userRepository;
        this.cryptConfig = cryptConfig;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(UserDTO userDTO) {
        return userRepository.save(User.builder()
                .username(userDTO.getUsername())
                .password(cryptConfig.getPasswordEncoder().encode(userDTO.getPassword()))
                .role(Role.ROLE_USER)
                .name_uk(userDTO.getName_uk())
                .name_en(userDTO.getName_en())
                .build());
    }
}
