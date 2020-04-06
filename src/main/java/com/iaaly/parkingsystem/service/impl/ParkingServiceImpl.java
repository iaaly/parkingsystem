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
    private final SlotTypeRepository slotTypeRepository;
    private final CarRepository carRepository;
    private final SlotRepository slotRepository;
    private final FloorRepository floorRepository;
    private final FloorSlotsRepository fLoorSlotsRepository;
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
        this.slotTypeRepository = slotTypeRepository;
        this.carRepository = carRepository;
        this.slotRepository = slotRepository;
        this.floorRepository = floorRepository;
        this.fLoorSlotsRepository = fLoorSlotsRepository;
        this.ticketRepository = ticketRepository;
        this.billRepository = billRepository;
        this.billingService = billingService;
    }

    @Override
    public Optional<PKTicket> bookSlot(String slotTypeKey, String carPlateNumber) {
        AtomicReference<Optional<PKTicket>> ticketHolder = new AtomicReference<>(Optional.empty());
        Optional<PKSlot> slot = slotRepository.findFirstByOccupiedAndSlotType_KeyOrderByFloorAsc(false, slotTypeKey);
        slot.ifPresent(pkSlot -> {
            PKCar pkCar = registerCar(pkSlot, carPlateNumber);
            PKTicket pkTicket = registerTicket(pkSlot, pkCar);
            ticketHolder.set(Optional.of(pkTicket));
        });
        return ticketHolder.get();
    }

    @Override
    public Optional<PKBill> clearSlot(long ticketId) {
        AtomicReference<Optional<PKBill>> billHolder = new AtomicReference<>(Optional.empty());
        Optional<PKTicket> ticket = ticketRepository.findById(ticketId);
        ticket.ifPresent(pkTicket -> {
            pkTicket.setCheckoutTime(new Date());
            PKBill bill = generateBill(pkTicket);
            billHolder.set(Optional.ofNullable(bill));
        });
        return billHolder.get();
    }

    private PKTicket registerTicket(PKSlot pkSlot, PKCar pkCar) {
        PKTicket pkTicket = PKTicket.builder()
                .car(pkCar)
                .checkinTime(new Date())
                .slot(pkSlot)
                .build();
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
