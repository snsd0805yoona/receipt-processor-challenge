package com.grace.receiptprocessor.service;

import com.grace.receiptprocessor.common.dto.ItemDTO;
import com.grace.receiptprocessor.common.entity.Receipt;
import com.grace.receiptprocessor.common.enums.Status;
import com.grace.receiptprocessor.common.exception.IdNotFoundException;
import com.grace.receiptprocessor.common.vo.PointResponseVO;
import com.grace.receiptprocessor.common.vo.ReceiptRequestVO;
import com.grace.receiptprocessor.common.vo.ReceiptResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ReceiptServiceImpl implements IReceiptService {
    private final List<Receipt> receiptList = new ArrayList<>();

    @Override
    public ReceiptResponseVO addReceipt(ReceiptRequestVO receiptRequestVO) {
        String id = UUID.randomUUID().toString();
        // check if id conflict
        while (checkIdExist(id) != null) {
            // if conflicted, regenerate one
            id = UUID.randomUUID().toString();
        }
        Receipt newReceipt = Receipt.builder()
                .id(id)
                .items(receiptRequestVO.getItems())
                .purchaseDate(receiptRequestVO.getPurchaseDate())
                .purchaseTime(receiptRequestVO.getPurchaseTime())
                .retailer(receiptRequestVO.getRetailer())
                .total(Double.parseDouble(receiptRequestVO.getTotal()))
                .build();
        receiptList.add(newReceipt);
        return ReceiptResponseVO.builder().id(id).build();
    }

    @Override
    public PointResponseVO getPoints(String id) {
        Receipt receipt = checkIdExist(id);
        if (receipt != null) {
            int points = 0;
            // One point for every alphanumeric
            points += countAlphaNumeric(receipt.getRetailer());

            // 50 points for round dollar with no cents
            if (receipt.getTotal() % 1 == 0) {
                points += 50;
            }

            // 25 points for multiple of 0.25
            if (receipt.getTotal() % 0.25 == 0) {
                points += 25;
            }

            if (receipt.getItems() != null) {
                // 5 points for every 2 items on the receipt
                points += 5 * (receipt.getItems().size() / 2);
                // If trimmed length of the item description is a multiple of 3
                points += checkTrimLengthItemDescriptionIsMultipleOfThree(receipt.getItems());
            }

            // if the day in the purchase date is odd
            if (receipt.getPurchaseDate().getDayOfMonth() % 2 == 1) {
                points += 6;
            }

            String[] time = receipt.getPurchaseTime().split(":");
            if (Integer.parseInt(time[0]) >= 14 && Integer.parseInt(time[0]) <= 16) {
                points += 10;
            }

            return PointResponseVO.builder().points(points).build();
        } else {
            log.info("id {} does not exist", id);
            throw new IdNotFoundException(Status.ID_NOT_FOUND);
        }
    }


    private Receipt checkIdExist(String id) {
        for (Receipt receipt : receiptList) {
            if (receipt.getId().equals(id)) {
                return receipt;
            }
        }

        return null;
    }

    private int countAlphaNumeric(String name) {
        int res = 0;
        for(int index = 0; index < name.length(); index++){
            char c = name.charAt(index);
            if ((c >= 'a' && c <='z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                res += 1;
            }
        }
        return res;
    }

    private int checkTrimLengthItemDescriptionIsMultipleOfThree(List<ItemDTO> items) {
        AtomicInteger res = new AtomicInteger();
        items.forEach(itemDTO -> {
            if (itemDTO.getShortDescription().trim().length() % 3 == 0) {
                res.addAndGet((int) Math.ceil(Double.parseDouble(itemDTO.getPrice()) * 0.2));
            }
        });
        return res.get();
    }
}
