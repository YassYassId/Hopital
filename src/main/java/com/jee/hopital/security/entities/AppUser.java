package com.jee.hopital.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    private String userId;
    @Column(unique=true)
    private String username;
    @Column(unique=true)
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
