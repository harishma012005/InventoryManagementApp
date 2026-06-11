package com.inventorymanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.LoginDTO;
import com.inventorymanagement.dto.RegisterUserDTO;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.security.jwt.JwtUtil;
import com.inventorymanagement.security.refresh.RefreshToken;
import com.inventorymanagement.security.refresh.RefreshTokenService;
import com.inventorymanagement.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO dto) {

        return ResponseEntity.ok(userService.saveUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        String email = auth.getName();
        String role = auth.getAuthorities().iterator().next().getAuthority();

        String accessToken = jwtUtil.generateToken(email, role);

        // 🔥 FIX: Get real user from DB (NOT null)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = refreshTokenService.createToken(user);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken(),
                "email", email,
                "role", role
        ));
    }

    // 🔁 REFRESH TOKEN
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody String refreshToken) {

        String newAccessToken =
                refreshTokenService.generateNewAccessToken(refreshToken);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken
        ));
    }

    // 🚪 LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String refreshToken) {

        refreshTokenService.deleteToken(refreshToken);

        return ResponseEntity.ok("Logged out successfully");
    }
}