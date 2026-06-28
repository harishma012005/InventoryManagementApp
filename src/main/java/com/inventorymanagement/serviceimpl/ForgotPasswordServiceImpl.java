package com.inventorymanagement.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.ForgotPasswordDTO;
import com.inventorymanagement.dto.ResetPasswordDTO;
import com.inventorymanagement.dto.VerifyOtpDTO;
import com.inventorymanagement.entity.PasswordResetToken;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.PasswordResetTokenRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.ForgotPasswordService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ForgotPasswordServiceImpl
        implements ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= GENERATE OTP =================

    private String generateOtp() {

        Random random = new Random();

        int otp = 100000 + random.nextInt(900000);

        return String.valueOf(otp);
    }

    // ================= FORGOT PASSWORD =================

    @Override
    public String forgotPassword(
            ForgotPasswordDTO dto) {

        User user =
                userRepository.findByEmail(
                        dto.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Email Not Registered"));

        tokenRepository.deleteByEmail(
                dto.getEmail());

        String otp =
                generateOtp();

        PasswordResetToken token =
                new PasswordResetToken();

        token.setEmail(
                dto.getEmail());

        token.setOtp(
                otp);

        token.setExpiryTime(
                LocalDateTime.now()
                        .plusMinutes(10));

        tokenRepository.save(token);

        return "OTP Generated Successfully : " + otp;
    }

    // ================= VERIFY OTP =================

    @Override
    public String verifyOtp(
            VerifyOtpDTO dto) {

        PasswordResetToken token =
                tokenRepository
                        .findByEmailAndOtp(
                                dto.getEmail(),
                                dto.getOtp())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid OTP"));

        if(token.getExpiryTime()
                .isBefore(
                        LocalDateTime.now())) {

            throw new RuntimeException(
                    "OTP Expired");
        }

        return "OTP Verified Successfully";
    }

    // ================= RESET PASSWORD =================

    @Override
    public String resetPassword(
            ResetPasswordDTO dto) {

        User user =
                userRepository.findByEmail(
                        dto.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found"));

        Optional<PasswordResetToken> token =
                tokenRepository.findByEmail(
                        dto.getEmail());

        if(token.isEmpty()) {

            throw new RuntimeException(
                    "OTP Verification Required");
        }

        if(token.get()
                .getExpiryTime()
                .isBefore(
                        LocalDateTime.now())) {

            throw new RuntimeException(
                    "OTP Expired");
        }

        user.setPassword(
                passwordEncoder.encode(
                        dto.getNewPassword()));

        userRepository.save(user);

        tokenRepository.deleteByEmail(
                dto.getEmail());

        return "Password Reset Successfully";
    }

}