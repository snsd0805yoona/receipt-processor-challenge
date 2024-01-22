package com.grace.receiptprocessor.service;

import com.grace.receiptprocessor.common.entity.Receipt;
import com.grace.receiptprocessor.common.vo.PointResponseVO;
import com.grace.receiptprocessor.common.vo.ReceiptRequestVO;
import com.grace.receiptprocessor.common.vo.ReceiptResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ReceiptServiceImpl implements IReceiptService{
    private final List<Receipt> receiptList = new ArrayList<>();

    @Override
    public ReceiptResponseVO addReceipt(ReceiptRequestVO receiptRequestVO) {
        String id = UUID.randomUUID().toString();
        // check if id conflict
        while(checkIdExist(id) != null) {
            // if conflict, regenerate one
            id = UUID.randomUUID().toString();
        }
        Receipt newReceipt = Receipt.builder()
                .id(id)
                .items(receiptRequestVO.getItems())
                .purchaseDate(receiptRequestVO.getPurchaseDate())
                .purchaseTime(receiptRequestVO.getPurchaseTime())
                .retailer(receiptRequestVO.getRetailer())
                .total(receiptRequestVO.getTotal())
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


        } else {
            log.info("This id does not exist");
        }
        return null;
    }


    private Receipt checkIdExist(String id) {
        for(Receipt receipt: receiptList) {
            if (receipt.getId().equals(id)){
                return receipt;
            }
        }

        return null;
    }

    private long countAlphaNumeric(String name) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(name);
        return matcher.results().count();
    }
}
