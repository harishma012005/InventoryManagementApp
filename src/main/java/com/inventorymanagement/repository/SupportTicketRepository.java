package com.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.SupportTicket;

@Repository
public interface SupportTicketRepository
        extends JpaRepository<SupportTicket, Integer> {

    // User's Tickets

    List<SupportTicket> findByUser_UserId(
            Integer userId);

    // Filter By Status

    List<SupportTicket> findByStatus(
            String status);

    // Filter By Priority

    List<SupportTicket> findByPriority(
            String priority);

    // User Tickets By Status

    List<SupportTicket>
    findByUser_UserIdAndStatus(
            Integer userId,
            String status);
}
