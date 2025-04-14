package com.jee.hopital.security.service;

import com.jee.hopital.security.entities.AppRole;
import com.jee.hopital.security.entities.AppUser;
import com.jee.hopital.security.repository.AppRoleRepository;
import com.jee.hopital.security.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser addNewUser(String username, String email,
                              String password, String confirmPassword) {
        if(appUserRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        if(!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }
        AppUser appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole != null) {
            throw new RuntimeException("Role already exists");
        }
        appRole = AppRole.builder()
                .role(role).build();

        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
