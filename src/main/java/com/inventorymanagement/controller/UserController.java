package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.ChangePasswordDTO;
import com.inventorymanagement.dto.LoginDTO;
import com.inventorymanagement.dto.RegisterUserDTO;
import com.inventorymanagement.dto.UserDTO;
import com.inventorymanagement.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {


@Autowired
private UserService userService;


// Register User
//@PostMapping({"/register","/save"})
//public ResponseEntity<UserDTO> saveUser(

  //      @Valid
    //    @RequestBody RegisterUserDTO registerUserDTO) {

    //UserDTO savedUser =
      //      userService.saveUser(
        //            registerUserDTO);

    //return ResponseEntity
      //      .status(HttpStatus.CREATED)
        //    .body(savedUser);
//}

// Get All Users
@GetMapping("/all")
public ResponseEntity<List<UserDTO>>
getAllUsers() {

    return ResponseEntity.ok(
            userService.getAllUsers());
}

// Get User By ID
@GetMapping("/get/{id}")
public ResponseEntity<UserDTO>
getUserById(

        @PathVariable Integer id) {

    return ResponseEntity.ok(
            userService.getUserById(id));
}

// Update User
@PutMapping("/update/{id}")
public ResponseEntity<UserDTO>
updateUser(

        @PathVariable Integer id,

        @Valid
        @RequestBody UserDTO userDTO) {

    return ResponseEntity.ok(
            userService.updateUser(
                    id,
                    userDTO));
}

// Delete User
@DeleteMapping("/delete/{id}")
public ResponseEntity<Map<String, Object>>
deleteUser(

        @PathVariable Integer id) {

    userService.deleteUser(id);

    Map<String, Object> response =
            new HashMap<>();

    response.put("status", 200);

    response.put(
            "message",
            "User Deleted Successfully");

    return ResponseEntity.ok(
            response);
}

// Search User By Name
@GetMapping("/search/{name}")
public ResponseEntity<List<UserDTO>>
searchUsers(

        @PathVariable String name) {

    return ResponseEntity.ok(
            userService.searchUsersByName(
                    name));
}

// Login
//@PostMapping("/login")
//public ResponseEntity<UserDTO>
//login(

  //      @RequestBody LoginDTO loginDTO) {

 //   return ResponseEntity.ok(
   //         userService.login(
     //               loginDTO));
//}

// Change Password
@PutMapping("/change-password/{id}")
public ResponseEntity<Map<String, Object>>
changePassword(

        @PathVariable Integer id,

        @RequestBody
        ChangePasswordDTO changePasswordDTO) {

    String message =
            userService.changePassword(
                    id,
                    changePasswordDTO);

    Map<String, Object> response =
            new HashMap<>();

    response.put("status", 200);
    response.put("message", message);

    return ResponseEntity.ok(
            response);
}

// Get User By Email
@GetMapping("/email/{email}")
public ResponseEntity<UserDTO>
getUserByEmail(

        @PathVariable String email) {

    return ResponseEntity.ok(
            userService.getUserByEmail(
                    email));
}

// Get Users By Role
@GetMapping("/role/{role}")
public ResponseEntity<List<UserDTO>>
getUsersByRole(

        @PathVariable String role) {

    return ResponseEntity.ok(
            userService.getUsersByRole(
                    role));
}

// Get Total Users
@GetMapping("/count")
public ResponseEntity<Map<String, Object>>
getTotalUsers() {

    Map<String, Object> response =
            new HashMap<>();

    response.put(
            "totalUsers",
            userService.getTotalUsers());

    return ResponseEntity.ok(
            response);
}


}
