package com.inventorymanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class SupportReplyDTO {

    @NotBlank
    private String reply;

    public String getReply() {
        return reply;
    }

    public void setReply(
            String reply) {
        this.reply = reply;
    }
}