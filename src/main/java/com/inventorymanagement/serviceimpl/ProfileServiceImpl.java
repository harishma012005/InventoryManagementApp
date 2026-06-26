package com.inventorymanagement.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.ChangePasswordDTO;
import com.inventorymanagement.dto.ProfileDTO;
import com.inventorymanagement.dto.UpdateProfileDTO;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= LOGGED IN USER =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found"));
    }

    // ================= DTO CONVERSION =================

    private ProfileDTO convertToDTO(
            User user) {

        ProfileDTO dto =
                new ProfileDTO();

        dto.setUserId(
                user.getUserId());

        dto.setFullName(
                user.getFullName());

        dto.setEmail(
                user.getEmail());

        dto.setPhone(
                user.getPhone());

        dto.setRole(
                user.getRole());

        return dto;
    }

    // ================= GET MY PROFILE =================

    @Override
    public ProfileDTO getMyProfile() {

        User user =
                getLoggedInUser();

        return convertToDTO(user);
    }

    // ================= UPDATE PROFILE =================

    @Override
    public ProfileDTO updateProfile(
            UpdateProfileDTO dto) {

        User user =
                getLoggedInUser();

        // Email already used by another user

        if (!user.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {

            throw new RuntimeException(
                    "Email Already Exists");
        }

        // Phone already used by another user

        if (!user.getPhone().equals(dto.getPhone())
                && userRepository.existsByPhone(dto.getPhone())) {

            throw new RuntimeException(
                    "Phone Number Already Exists");
        }

        user.setFullName(
                dto.getFullName());

        user.setEmail(
                dto.getEmail());

        user.setPhone(
                dto.getPhone());

        User updatedUser =
                userRepository.save(user);

        return convertToDTO(
                updatedUser);
    }

    // ================= CHANGE PASSWORD =================

    @Override
    public String changePassword(
            ChangePasswordDTO dto) {

        User user =
                getLoggedInUser();

        boolean passwordMatches =
                passwordEncoder.matches(
                        dto.getOldPassword(),
                        user.getPassword());

        if (!passwordMatches) {

            throw new RuntimeException(
                    "Old Password Is Incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(
                        dto.getNewPassword()));

        userRepository.save(user);

        return "Password Changed Successfully";
    }
}