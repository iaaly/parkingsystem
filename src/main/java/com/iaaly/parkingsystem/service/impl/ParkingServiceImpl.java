package com.iaaly.parkingsystem.service.impl;

import com.iaaly.parkingsystem.entity.PKBill;
import com.iaaly.parkingsystem.entity.PKCar;
import com.iaaly.parkingsystem.entity.PKSlot;
import com.iaaly.parkingsystem.entity.PKTicket;
import com.iaaly.parkingsystem.respository.*;
import com.iaaly.parkingsystem.service.BillingService;
import com.iaaly.parkingsystem.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ParkingServiceImpl implements ParkingService {
    private final CarRepository carRepository;
    private final SlotRepository slotRepository;
    private final TicketRepository ticketRepository;
    private final BillRepository billRepository;

    private final BillingService billingService;

    @Autowired
    public ParkingServiceImpl(SlotTypeRepository slotTypeRepository,
                              CarRepository carRepository,
                              SlotRepository slotRepository,
                              FloorRepository floorRepository,
                              FloorSlotsRepository fLoorSlotsRepository,
                              TicketRepository ticketRepository,
                              BillRepository billRepository,
                              BillingService billingService) {
        this.carRepository = carRepository;
        this.slotRepository = slotRepository;
        this.ticketRepository = ticketRepository;
        this.billRepository = billRepository;
        this.billingService = billingService;
    }

    @Override
    public Optional<PKTicket> bookSlot(String slotTypeKey, String carPlateNumber) {
        // Initialize a ticket holder
        AtomicReference<Optional<PKTicket>> ticketHolder = new AtomicReference<>(Optional.empty());
        // Find the first available slot to book
        Optional<PKSlot> slot = slotRepository.findFirstByOccupiedAndSlotType_KeyOrderByFloorAsc(false, slotTypeKey);
        slot.ifPresent(pkSlot -> {
            // Generate customer ticket
            PKCar pkCar = registerCar(pkSlot, carPlateNumber);
            PKTicket pkTicket = registerTicket(pkSlot, pkCar);
            ticketHolder.set(Optional.of(pkTicket));
        });
        // Return ticket
        return ticketHolder.get();
    }

    @Override
    public Optional<PKBill> clearSlot(long ticketId) {
        // Initialize a bill holder
        AtomicReference<Optional<PKBill>> billHolder = new AtomicReference<>(Optional.empty());
        // Find the ticket to bill
        Optional<PKTicket> ticket = ticketRepository.findById(ticketId);
        ticket.ifPresent(pkTicket -> {
            // Validate that the ticket wasn't already paid
            if (pkTicket.getCheckoutTime() == null) {
                pkTicket.setCheckoutTime(new Date());
                // Generate the bill
                PKBill bill = generateBill(pkTicket);
                billHolder.set(Optional.of(bill));
                // Update ticket Checkout date and mark it as paid
                ticketRepository.save(pkTicket);
            }
        });
        // Return bill
        return billHolder.get();
    }

    private PKTicket registerTicket(PKSlot pkSlot, PKCar pkCar) {
        PKTicket pkTicket = PKTicket.builder()
                .car(pkCar)
                .checkinTime(new Date())
                .slot(pkSlot)
                .build();

        String friendlyInstructions = String.format("Your slot is at floor %s, number %s. Car plate number: %s",
                pkTicket.getSlot().getFloor().getName(),
                pkTicket.getSlot().getIdentifier(),
                pkTicket.getSlot().getCustomerCar().getPlateNumber());

        pkTicket.setInstructions(friendlyInstructions);

        return ticketRepository.save(pkTicket);
    }

    private PKCar registerCar(PKSlot slot, String carPlateNumber) {
        // lookup or register the car
        PKCar pkCar = carRepository
                .findByPlateNumber(carPlateNumber)
                .orElseGet(() -> carRepository.save(
                        PKCar.builder()
                                .plateNumber(carPlateNumber)
                                .build()
                ));

        slot.setCustomerCar(pkCar);
        slot.setOccupied(true);
        slotRepository.save(slot);
        return pkCar;
    }

    private PKBill generateBill(PKTicket pkTicket) {
        BigDecimal amount = billingService.calculateBill(pkTicket);

        PKBill bill = PKBill.builder()
                .amount(amount)
                .currency("EUR")
                .refNo(UUID.randomUUID().toString())
                .build();
        return billRepository.save(bill);
    }
}
