package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.AdminRefundActionDTO;
import com.inventorymanagement.dto.CreateRefundDTO;
import com.inventorymanagement.dto.RefundDTO;
import com.inventorymanagement.dto.RefundResponseDTO;

public interface RefundService {

    // ================= USER =================

    RefundResponseDTO requestRefund(
            CreateRefundDTO dto);

    List<RefundDTO> getMyRefunds();

    RefundDTO getRefundById(
            Integer refundId);

    // ================= ADMIN =================

    List<RefundDTO> getAllRefunds();

    List<RefundDTO> getRefundsByStatus(
            String refundStatus);

    RefundResponseDTO approveRefund(
            Integer refundId,
            AdminRefundActionDTO dto);

    RefundResponseDTO rejectRefund(
            Integer refundId,
            AdminRefundActionDTO dto);

    void deleteRefund(
            Integer refundId);
}