package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.ChangePasswordDTO;
import com.inventorymanagement.dto.LoginDTO;
import com.inventorymanagement.dto.RegisterUserDTO;
import com.inventorymanagement.dto.UserDTO;

public interface UserService {

    // Register User
    UserDTO saveUser(
            RegisterUserDTO registerUserDTO);

    // Get All Users
    List<UserDTO> getAllUsers();

    // Get User By ID
    UserDTO getUserById(
            Integer id);

    // Update User
    UserDTO updateUser(
            Integer id,
            UserDTO userDTO);

    // Delete User
    void deleteUser(
            Integer id);

    // Search User By Name
    List<UserDTO> searchUsersByName(
            String name);

    // Login
    UserDTO login(
            LoginDTO loginDTO);

    // Change Password
    String changePassword(
            Integer id,
            ChangePasswordDTO changePasswordDTO);

    // Search User By Email
    UserDTO getUserByEmail(
            String email);

    // Filter User By Role
    List<UserDTO> getUsersByRole(
            String role);

    // Total User Count
    Long getTotalUsers();
}