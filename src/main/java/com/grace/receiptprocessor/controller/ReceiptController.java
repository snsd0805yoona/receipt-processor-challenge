package com.grace.receiptprocessor.controller;

import com.grace.receiptprocessor.common.ApiResponse;
import com.grace.receiptprocessor.common.enums.Status;
import com.grace.receiptprocessor.common.exception.IdNotFoundException;
import com.grace.receiptprocessor.common.vo.PointResponseVO;
import com.grace.receiptprocessor.common.vo.ReceiptRequestVO;
import com.grace.receiptprocessor.common.vo.ReceiptResponseVO;
import com.grace.receiptprocessor.service.IReceiptService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    @Autowired
    private IReceiptService receiptService;

    @PostMapping("/process")
    public ApiResponse<ReceiptResponseVO> addReceipt(@Valid @RequestBody ReceiptRequestVO receiptRequestVO) {
        return ApiResponse.success(receiptService.addReceipt(receiptRequestVO));
    }

    @GetMapping("/{id}/points")
    public ApiResponse<PointResponseVO> getPoints(@PathVariable String id) {
        try {
            PointResponseVO result = receiptService.getPoints(id);
            return ApiResponse.success(result);
        } catch (IdNotFoundException e) {
            return ApiResponse.error(Status.getStatusEnumByStatus(e.getCode()), e.getMessage());
        }
    }
}
