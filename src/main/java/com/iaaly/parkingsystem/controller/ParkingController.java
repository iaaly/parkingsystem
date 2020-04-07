package com.iaaly.parkingsystem.controller;

import com.iaaly.parkingsystem.dto.request.CheckinRequest;
import com.iaaly.parkingsystem.dto.request.CheckoutRequest;
import com.iaaly.parkingsystem.dto.response.BaseResponse;
import com.iaaly.parkingsystem.dto.response.CheckinResponse;
import com.iaaly.parkingsystem.dto.response.CheckoutResponse;
import com.iaaly.parkingsystem.entity.PKBill;
import com.iaaly.parkingsystem.entity.PKTicket;
import com.iaaly.parkingsystem.service.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParkingController.class);
    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Transactional
    @PostMapping(value = "/checkin")
    public CheckinResponse checkinRequest(@Valid @RequestBody CheckinRequest checkinRequest) {

        LOGGER.info("Received Checkin request: {}", checkinRequest);

        Optional<PKTicket> ticket = parkingService.bookSlot(checkinRequest.getSlotTypeKey(),
                        checkinRequest.getCarPlateNumber());

        CheckinResponse checkinResponse;

        if (ticket.isPresent()) {
            checkinResponse = CheckinResponse.builder()
                    .ticketId(ticket.get().getId())
                    .slotName(ticket.get().getSlot().getIdentifier())
                    .build();
            checkinResponse.setMessage(ticket.get().getInstructions());
        } else {
            checkinResponse = new CheckinResponse();
            checkinResponse.setStatus(BaseResponse.FAIL);
            checkinResponse.setMessage("No places available, please comeback later");
        }

        LOGGER.info("Returning Checkin response: {}", checkinResponse);

        return checkinResponse;

    }

    @Transactional
    @PostMapping(value = "/checkout")
    public CheckoutResponse checkoutRequest(@Valid @RequestBody CheckoutRequest checkoutRequest) {

        LOGGER.info("Received Checkout request: {}", checkoutRequest);

        Optional<PKBill> bill = parkingService.clearSlot(checkoutRequest.getTicketId());

        CheckoutResponse checkoutResponse;

        if (bill.isPresent()) {
            checkoutResponse = CheckoutResponse.builder()
                    .billAmount(bill.get().getAmount())
                    .billCurrency(bill.get().getCurrency())
                    .billRefNo(bill.get().getRefNo())
                    .build();
            checkoutResponse.setMessage("Take care and see you soon!");
        } else {
            checkoutResponse = new CheckoutResponse();
            checkoutResponse.setStatus(BaseResponse.FAIL);
            checkoutResponse.setMessage("Your ticket is either old or invalid.");
        }

        LOGGER.info("Returning Checkout response: {}", checkoutResponse);

        return checkoutResponse;
    }

}
