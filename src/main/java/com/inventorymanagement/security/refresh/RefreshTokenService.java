package com.inventorymanagement.security.refresh;

import com.inventorymanagement.entity.User;

public interface RefreshTokenService {

    RefreshToken createToken(User user);

    RefreshToken verifyToken(String token);

    String generateNewAccessToken(String refreshToken);

    void deleteToken(String token);
}