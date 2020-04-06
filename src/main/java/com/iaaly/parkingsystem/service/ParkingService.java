package com.iaaly.parkingsystem.service;

import com.iaaly.parkingsystem.entity.PKBill;
import com.iaaly.parkingsystem.entity.PKTicket;

import java.util.Optional;

public interface ParkingService {
    Optional<PKTicket> bookSlot(String slotTypeKey, String carPlateNumber);
    Optional<PKBill> clearSlot(long ticketId);
}
