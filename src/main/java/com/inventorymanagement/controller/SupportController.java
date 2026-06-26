package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.CreateSupportTicketDTO;
import com.inventorymanagement.dto.SupportReplyDTO;
import com.inventorymanagement.dto.SupportResponseDTO;
import com.inventorymanagement.dto.SupportStatusDTO;
import com.inventorymanagement.dto.SupportTicketDTO;
import com.inventorymanagement.service.SupportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private SupportService supportService;

    // ================= USER =================

    @PostMapping("/create")
    public ResponseEntity<SupportResponseDTO> createTicket(
            @Valid @RequestBody CreateSupportTicketDTO dto) {

        return ResponseEntity.ok(
                supportService.createTicket(dto));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<SupportTicketDTO>> getMyTickets() {

        return ResponseEntity.ok(
                supportService.getMyTickets());
    }

    @GetMapping("/my-ticket/{ticketId}")
    public ResponseEntity<SupportTicketDTO> getMyTicketById(
            @PathVariable Integer ticketId) {

        return ResponseEntity.ok(
                supportService.getMyTicketById(ticketId));
    }

    // ================= ADMIN =================

    @GetMapping("/all")
    public ResponseEntity<List<SupportTicketDTO>> getAllTickets() {

        return ResponseEntity.ok(
                supportService.getAllTickets());
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<SupportTicketDTO> getTicketById(
            @PathVariable Integer ticketId) {

        return ResponseEntity.ok(
                supportService.getTicketById(ticketId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SupportTicketDTO>> getTicketsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                supportService.getTicketsByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<SupportTicketDTO>> getTicketsByPriority(
            @PathVariable String priority) {

        return ResponseEntity.ok(
                supportService.getTicketsByPriority(priority));
    }

    @PutMapping("/reply/{ticketId}")
    public ResponseEntity<SupportResponseDTO> replyToTicket(
            @PathVariable Integer ticketId,
            @Valid @RequestBody SupportReplyDTO dto) {

        return ResponseEntity.ok(
                supportService.replyToTicket(
                        ticketId,
                        dto));
    }

    @PutMapping("/status/{ticketId}")
    public ResponseEntity<SupportResponseDTO> updateTicketStatus(
            @PathVariable Integer ticketId,
            @Valid @RequestBody SupportStatusDTO dto) {

        return ResponseEntity.ok(
                supportService.updateTicketStatus(
                        ticketId,
                        dto));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable Integer ticketId) {

        supportService.deleteTicket(ticketId);

        return ResponseEntity.ok(
                "Support Ticket Deleted Successfully");
    }
}