package com.inventorymanagement.service;

import com.inventorymanagement.dto.ForgotPasswordDTO;
import com.inventorymanagement.dto.ResetPasswordDTO;
import com.inventorymanagement.dto.VerifyOtpDTO;

public interface ForgotPasswordService {

    // ================= FORGOT PASSWORD =================

    String forgotPassword(
            ForgotPasswordDTO dto);

    // ================= VERIFY OTP =================

    String verifyOtp(
            VerifyOtpDTO dto);

    // ================= RESET PASSWORD =================

    String resetPassword(
            ResetPasswordDTO dto);
}