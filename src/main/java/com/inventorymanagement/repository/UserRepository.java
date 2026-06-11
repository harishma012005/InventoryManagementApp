
package com.inventorymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.User;

@Repository
public interface UserRepository
        extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<User> findByEmail(String email);

    List<User> findByFullNameContainingIgnoreCase(
            String fullName);
    List<User> findByRole(String role);
    
   }