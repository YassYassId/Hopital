package com.jee.hopital.security.service;

import com.jee.hopital.security.entities.AppRole;
import com.jee.hopital.security.entities.AppUser;

public interface AccountService {

    AppUser addNewUser(String username, String email,
                       String password, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);

}
