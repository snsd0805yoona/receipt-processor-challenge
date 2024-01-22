package com.grace.receiptprocessor.common.entity;

import com.grace.receiptprocessor.common.dto.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Receipt {
    private String id;
    private String retailer;
    private LocalDate purchaseDate;
    private String purchaseTime;
    private Double total;
    private List<ItemDTO> items;
}
