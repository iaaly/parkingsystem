package com.iaaly.parkingsystem.service;

import com.iaaly.parkingsystem.entity.PKTicket;

import java.math.BigDecimal;

public interface BillingService {
    BigDecimal calculateBill(PKTicket ticket);
}
