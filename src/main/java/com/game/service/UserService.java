package com.game.service;

import com.game.config.CryptConfig;
import com.game.dto.UserDTO;
import com.game.entity.Role;
import com.game.entity.User;
import com.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User saveUser(UserDTO userDTO) {
        return userRepository.save(User.builder()
                .username(userDTO.getUsername())
                .password(cryptConfig.getPasswordEncoder().encode(userDTO.getPassword()))
                .role(Role.ROLE_USER)
                .nameUk(userDTO.getNameUk())
                .nameEn(userDTO.getNameEn())
                .build());
    }
}
