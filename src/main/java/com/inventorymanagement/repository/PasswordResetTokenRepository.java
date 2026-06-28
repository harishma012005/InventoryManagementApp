package com.inventorymanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Integer> {

    // Find OTP by Email
    Optional<PasswordResetToken> findByEmail(
            String email);

    // Find OTP using Email and OTP
    Optional<PasswordResetToken> findByEmailAndOtp(
            String email,
            String otp);

    // Delete existing OTP by Email
    void deleteByEmail(
            String email);
}