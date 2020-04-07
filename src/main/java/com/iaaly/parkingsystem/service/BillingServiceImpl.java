package com.iaaly.parkingsystem.service;

import com.iaaly.parkingsystem.entity.PKSlotType;
import com.iaaly.parkingsystem.entity.PKTicket;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BillingServiceImpl implements BillingService {
    @Override
    public BigDecimal calculateBill(PKTicket ticket) {

        Date checkout = ticket.getCheckoutTime();
        Date checkin = ticket.getCheckinTime();
        PKSlotType slotType = ticket.getSlot().getSlotType();

        long millis = checkout.getTime() - checkin.getTime();
        // Round the calculation to the nearest hour
        long hours = Math.round((double)millis / 3600000);

        double hourlyRate = slotType.getPricingPolicyAttributes().getHourlyRate();
        double fixedRate = slotType.getPricingPolicyAttributes().getFixedRate();

        return BigDecimal.valueOf(fixedRate + hourlyRate * hours);
    }
}
