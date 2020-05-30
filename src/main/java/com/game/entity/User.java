package com.game.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "name_uk", nullable = false)
    private String nameUk;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @OneToMany(mappedBy = "user")
    Set<Round> rounds;
}
