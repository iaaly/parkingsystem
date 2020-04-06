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
        BigDecimal hours = BigDecimal.valueOf(millis / 3600000);

        BigDecimal hourlyRate = BigDecimal.valueOf(3);
        BigDecimal fixedRate;
        switch (slotType.getKey()) {
            case "B":
                fixedRate = BigDecimal.valueOf(5);
                break;
            case "C":
                fixedRate = BigDecimal.valueOf(10);
                break;
            default:
                fixedRate = BigDecimal.ZERO;
                break;
        }

        return fixedRate.add(hourlyRate.multiply(hours));
    }
}
