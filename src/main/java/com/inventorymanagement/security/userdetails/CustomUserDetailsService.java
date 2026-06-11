package com.inventorymanagement.security.userdetails;

import com.inventorymanagement.entity.User;
import com.inventorymanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 🔍 fetch user from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // 🎭 convert role to Spring Security format
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + user.getRole());

        // 🔐 return Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(authority)
        );
    }
}