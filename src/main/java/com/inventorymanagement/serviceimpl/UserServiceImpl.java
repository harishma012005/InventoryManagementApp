package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.ChangePasswordDTO;
import com.inventorymanagement.dto.LoginDTO;
import com.inventorymanagement.dto.RegisterUserDTO;
import com.inventorymanagement.dto.UserDTO;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;	
@Autowired
private UserRepository userRepository;

// Entity -> DTO
private UserDTO convertToDTO(User user) {

    UserDTO dto = new UserDTO();

    dto.setUserId(user.getUserId());
    dto.setFullName(user.getFullName());
    dto.setEmail(user.getEmail());
    dto.setPhone(user.getPhone());
    dto.setRole("USER");

    return dto;
}

// Register DTO -> Entity
private User convertToEntity(RegisterUserDTO dto) {

    User user = new User();

    user.setFullName(dto.getFullName());
    user.setEmail(dto.getEmail());
    user.setPassword(
            passwordEncoder.encode(dto.getPassword())
    );
    user.setPhone(dto.getPhone());
    user.setRole("USER");

    return user;
}

// Register User
@Override
public UserDTO saveUser(RegisterUserDTO registerUserDTO) {

    if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
        throw new RuntimeException("Email Already Exists");
    }

    if (userRepository.existsByPhone(registerUserDTO.getPhone())) {
        throw new RuntimeException("Phone Number Already Exists");
    }
    User user =convertToEntity(registerUserDTO );
   

    return convertToDTO(userRepository.save(user));
}

// Get All Users
@Override
public List<UserDTO> getAllUsers() {

    return userRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .toList();
}

// Get User By ID
@Override
public UserDTO getUserById(
        Integer id) {

    User user =
            userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User Not Found With ID : "
                                    + id));

    return convertToDTO(user);
}

// Update User
@Override
public UserDTO updateUser(
        Integer id,
        UserDTO userDTO) {

    User existingUser =
            userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User Not Found With ID : "
                                    + id));

    existingUser.setFullName(
            userDTO.getFullName());

    existingUser.setEmail(
            userDTO.getEmail());

    existingUser.setPhone(
            userDTO.getPhone());

    existingUser.setRole(
            userDTO.getRole());

    User updatedUser =
            userRepository.save(existingUser);

    return convertToDTO(updatedUser);
}

// Delete User
@Override
public void deleteUser(
        Integer id) {

    User user =
            userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User Not Found With ID : "
                                    + id));

    userRepository.delete(user);
}

// Search User By Name
@Override
public List<UserDTO> searchUsersByName(
        String name) {

    return userRepository
            .findByFullNameContainingIgnoreCase(name)
            .stream()
            .map(this::convertToDTO)
            .toList();
}

// Login
@Override
public UserDTO login(LoginDTO loginDTO) {

    User user = userRepository.findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid Email"));

    // ✅ FIX HERE
    if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid Password");
    }

    return convertToDTO(user);
}
// Change Password
@Override
public String changePassword(
        Integer id,
        ChangePasswordDTO changePasswordDTO) {

    User user =
            userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User Not Found"));

    if (!user.getPassword()
            .equals(
                    changePasswordDTO.getOldPassword())) {

        throw new RuntimeException(
                "Old Password Is Incorrect");
    }

    user.setPassword(
            changePasswordDTO.getNewPassword());

    userRepository.save(user);

    return "Password Updated Successfully";
}

// Search User By Email
@Override
public UserDTO getUserByEmail(
        String email) {

    User user =
            userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User Not Found With Email : "
                                    + email));

    return convertToDTO(user);
}

// Get Users By Role
@Override
public List<UserDTO> getUsersByRole(
        String role) {

    return userRepository.findByRole(role)
            .stream()
            .map(this::convertToDTO)
            .toList();
}

// Total User Count
@Override
public Long getTotalUsers() {

    return userRepository.count();
}


}

