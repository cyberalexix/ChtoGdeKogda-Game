package com.game.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.FetchMode.SELECT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {
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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = SELECT)
    List<Round> rounds = new ArrayList<>();
}
