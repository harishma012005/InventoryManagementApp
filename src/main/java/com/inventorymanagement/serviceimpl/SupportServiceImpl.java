package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.dto.CreateSupportTicketDTO;
import com.inventorymanagement.dto.SupportReplyDTO;
import com.inventorymanagement.dto.SupportResponseDTO;
import com.inventorymanagement.dto.SupportStatusDTO;
import com.inventorymanagement.dto.SupportTicketDTO;
import com.inventorymanagement.entity.SupportTicket;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.SupportTicketRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.NotificationService;
import com.inventorymanagement.service.SupportService;

@Service
public class SupportServiceImpl implements SupportService {

    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = false)
    private NotificationService notificationService;

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

    private SupportTicketDTO convertToDTO(
            SupportTicket ticket) {

        SupportTicketDTO dto =
                new SupportTicketDTO();

        dto.setTicketId(
                ticket.getTicketId());

        dto.setUserId(
                ticket.getUser()
                        .getUserId());

        dto.setCustomerName(
                ticket.getUser()
                        .getFullName());

        dto.setSubject(
                ticket.getSubject());

        dto.setMessage(
                ticket.getMessage());

        dto.setReply(
                ticket.getReply());

        dto.setStatus(
                ticket.getStatus());

        dto.setPriority(
                ticket.getPriority());

        dto.setCreatedAt(
                ticket.getCreatedAt());

        dto.setUpdatedAt(
                ticket.getUpdatedAt());

        return dto;
    }

    // ================= CREATE TICKET =================

    @Override
    public SupportResponseDTO createTicket(
            CreateSupportTicketDTO dto) {

        User user =
                getLoggedInUser();

        SupportTicket ticket =
                new SupportTicket();

        ticket.setUser(user);

        ticket.setSubject(
                dto.getSubject());

        ticket.setMessage(
                dto.getMessage());

        ticket.setPriority(
                dto.getPriority());

        ticket.setStatus(
                "OPEN");

        SupportTicket savedTicket =
                supportTicketRepository
                        .save(ticket);

        // Notification Integration

        if (notificationService != null) {

            CreateNotificationDTO notification =
                    new CreateNotificationDTO();

            notification.setUserId(
                    user.getUserId());

            notification.setTitle(
                    "SUPPORT TICKET CREATED");

            notification.setMessage(
                    "Your Ticket #"
                            + savedTicket.getTicketId()
                            + " has been created successfully.");

            notification.setType(
                    "SUPPORT");

            notificationService
                    .createNotification(
                            notification);
        }

        SupportResponseDTO response =
                new SupportResponseDTO();

        response.setTicketId(
                savedTicket.getTicketId());

        response.setMessage(
                "Support Ticket Created Successfully");

        return response;
    }

    // ================= MY TICKETS =================

    @Override
    public List<SupportTicketDTO>
    getMyTickets() {

        User user =
                getLoggedInUser();

        return supportTicketRepository
                .findByUser_UserId(
                        user.getUserId())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= MY TICKET BY ID =================

    @Override
    public SupportTicketDTO getMyTicketById(
            Integer ticketId) {

        User user =
                getLoggedInUser();

        SupportTicket ticket =
                supportTicketRepository
                        .findById(ticketId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ticket Not Found"));

        if (!ticket.getUser()
                .getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "Access Denied");
        }

        return convertToDTO(ticket);
    }

    // ================= ALL TICKETS =================

    @Override
    public List<SupportTicketDTO>
    getAllTickets() {

        return supportTicketRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= TICKET BY ID =================

    @Override
    public SupportTicketDTO getTicketById(
            Integer ticketId) {

        SupportTicket ticket =
                supportTicketRepository
                        .findById(ticketId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ticket Not Found"));

        return convertToDTO(ticket);
    }

    // ================= STATUS FILTER =================

    @Override
    public List<SupportTicketDTO>
    getTicketsByStatus(
            String status) {

        return supportTicketRepository
                .findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= PRIORITY FILTER =================

    @Override
    public List<SupportTicketDTO>
    getTicketsByPriority(
            String priority) {

        return supportTicketRepository
                .findByPriority(priority)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= REPLY =================

    @Override
    public SupportResponseDTO replyToTicket(
            Integer ticketId,
            SupportReplyDTO dto) {

        SupportTicket ticket =
                supportTicketRepository
                        .findById(ticketId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ticket Not Found"));

        ticket.setReply(
                dto.getReply());

        supportTicketRepository
                .save(ticket);

        SupportResponseDTO response =
                new SupportResponseDTO();

        response.setTicketId(
                ticket.getTicketId());

        response.setMessage(
                "Reply Added Successfully");

        return response;
    }

    // ================= UPDATE STATUS =================

    @Override
    public SupportResponseDTO updateTicketStatus(
            Integer ticketId,
            SupportStatusDTO dto) {

        SupportTicket ticket =
                supportTicketRepository
                        .findById(ticketId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ticket Not Found"));

        ticket.setStatus(
                dto.getStatus());

        supportTicketRepository
                .save(ticket);

        // Notification Integration

        if ("RESOLVED".equalsIgnoreCase(
                dto.getStatus())
                && notificationService != null) {

            CreateNotificationDTO notification =
                    new CreateNotificationDTO();

            notification.setUserId(
                    ticket.getUser()
                            .getUserId());

            notification.setTitle(
                    "SUPPORT TICKET RESOLVED");

            notification.setMessage(
                    "Your Ticket #"
                            + ticket.getTicketId()
                            + " has been resolved.");

            notification.setType(
                    "SUPPORT");

            notificationService
                    .createNotification(
                            notification);
        }

        SupportResponseDTO response =
                new SupportResponseDTO();

        response.setTicketId(
                ticket.getTicketId());

        response.setMessage(
                "Ticket Status Updated Successfully");

        return response;
    }

    // ================= DELETE =================

    @Override
    public void deleteTicket(
            Integer ticketId) {

        SupportTicket ticket =
                supportTicketRepository
                        .findById(ticketId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ticket Not Found"));

        supportTicketRepository
                .delete(ticket);
    }
}