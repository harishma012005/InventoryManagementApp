package com.inventorymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.ForgotPasswordDTO;
import com.inventorymanagement.dto.ResetPasswordDTO;
import com.inventorymanagement.dto.VerifyOtpDTO;
import com.inventorymanagement.service.ForgotPasswordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    // ================= FORGOT PASSWORD =================

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordDTO dto) {

        return new ResponseEntity<>(
                forgotPasswordService.forgotPassword(dto),
                HttpStatus.OK);
    }

    // ================= VERIFY OTP =================

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @Valid @RequestBody VerifyOtpDTO dto) {

        return new ResponseEntity<>(
                forgotPasswordService.verifyOtp(dto),
                HttpStatus.OK);
    }

    // ================= RESET PASSWORD =================

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordDTO dto) {

        return new ResponseEntity<>(
                forgotPasswordService.resetPassword(dto),
                HttpStatus.OK);
    }
}
