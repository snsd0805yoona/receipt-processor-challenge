package com.grace.receiptprocessor.common.vo;

import com.grace.receiptprocessor.common.dto.ItemDTO;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReceiptRequestVO {
    private String retailer;
    private LocalDate purchaseDate;
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Please enter valid time format, ex: 01:01")
    private String purchaseTime;
    private Double total;
    private List<ItemDTO> items;
}
