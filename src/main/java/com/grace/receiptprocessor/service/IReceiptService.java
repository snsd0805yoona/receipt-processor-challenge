package com.grace.receiptprocessor.service;

import com.grace.receiptprocessor.common.entity.Receipt;
import com.grace.receiptprocessor.common.vo.PointResponseVO;
import com.grace.receiptprocessor.common.vo.ReceiptRequestVO;
import com.grace.receiptprocessor.common.vo.ReceiptResponseVO;

public interface IReceiptService {
    ReceiptResponseVO addReceipt(ReceiptRequestVO receiptRequestVO);
    PointResponseVO getPoints(String id);
}
