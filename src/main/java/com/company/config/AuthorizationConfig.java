package com.company.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationConfig {
    public static String getCurrentProfileUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
//
//        // User user = (User) authentication.getPrincipal();
//        // List<SimpleGrantedAuthority> rolesLIst = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
//        // authentication.getCredentials()
        return userName;

    }
}
