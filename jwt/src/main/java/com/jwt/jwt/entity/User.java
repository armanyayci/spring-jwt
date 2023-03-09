package com.jwt.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user", sequenceName = "UserIDGenerator")
    private int id;

    @Column(name = "Name")
    private String name;
    @Column(name = "Username")
    private String Username;

    @Column(name = "password")
    private String password;
    @Column(name = "Email")
    private String email;
    @Column(name = "isActive")
    private boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL
            , fetch = FetchType.EAGER)
    @JoinTable(name = "user_role"
            , joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;
}

