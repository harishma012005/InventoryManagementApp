package com.inventorymanagement.security.refresh;

import com.inventorymanagement.entity.User;
import com.inventorymanagement.security.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public RefreshToken createToken(User user) {

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(7));

        return repository.save(token);
    }

    @Override
    public RefreshToken verifyToken(String token) {

        RefreshToken rt = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

        if (rt.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh Token Expired");
        }

        return rt;
    }

    @Override
    public String generateNewAccessToken(String refreshToken) {

        RefreshToken rt = verifyToken(refreshToken);

        User user = rt.getUser();

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        repository.deleteByToken(token);
    }
}