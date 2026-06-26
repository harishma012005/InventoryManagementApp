package com.inventorymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.ChangePasswordDTO;
import com.inventorymanagement.dto.ProfileDTO;
import com.inventorymanagement.dto.UpdateProfileDTO;
import com.inventorymanagement.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // ================= GET MY PROFILE =================

    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> getMyProfile() {

        return ResponseEntity.ok(
                profileService.getMyProfile());
    }

    // ================= UPDATE PROFILE =================

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> updateProfile(
            @Valid
            @RequestBody
            UpdateProfileDTO dto) {

        return ResponseEntity.ok(
                profileService.updateProfile(dto));
    }

    // ================= CHANGE PASSWORD =================

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid
            @RequestBody
            ChangePasswordDTO dto) {

        return ResponseEntity.ok(
                profileService.changePassword(dto));
    }
}