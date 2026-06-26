package com.inventorymanagement.service;

import com.inventorymanagement.dto.ChangePasswordDTO;
import com.inventorymanagement.dto.ProfileDTO;
import com.inventorymanagement.dto.UpdateProfileDTO;

public interface ProfileService {

    // View Logged-in User Profile
    ProfileDTO getMyProfile();

    // Update Logged-in User Profile
    ProfileDTO updateProfile(
            UpdateProfileDTO dto);

    // Change Password
    String changePassword(
            ChangePasswordDTO dto);
}