package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.CreateSupportTicketDTO;
import com.inventorymanagement.dto.SupportReplyDTO;
import com.inventorymanagement.dto.SupportResponseDTO;
import com.inventorymanagement.dto.SupportStatusDTO;
import com.inventorymanagement.dto.SupportTicketDTO;

public interface SupportService {

    // ================= USER =================

    // Create Ticket
    SupportResponseDTO createTicket(
            CreateSupportTicketDTO dto);

    // My Tickets
    List<SupportTicketDTO> getMyTickets();

    // My Ticket By Id
    SupportTicketDTO getMyTicketById(
            Integer ticketId);

    // ================= ADMIN =================

    // All Tickets
    List<SupportTicketDTO> getAllTickets();

    // Ticket By Id
    SupportTicketDTO getTicketById(
            Integer ticketId);

    // Tickets By Status
    List<SupportTicketDTO> getTicketsByStatus(
            String status);

    // Tickets By Priority
    List<SupportTicketDTO> getTicketsByPriority(
            String priority);

    // Reply To Ticket
    SupportResponseDTO replyToTicket(
            Integer ticketId,
            SupportReplyDTO dto);

    // Update Status
    SupportResponseDTO updateTicketStatus(
            Integer ticketId,
            SupportStatusDTO dto);

    // Delete Ticket
    void deleteTicket(
            Integer ticketId);
}