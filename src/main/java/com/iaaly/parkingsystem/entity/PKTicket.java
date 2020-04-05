package com.iaaly.parkingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKTicket {
    @Id
    @GeneratedValue
    private long id;

    @JoinColumn(name = "slot_id")
    @ManyToOne
    private PKSlot slot;

    @JoinColumn(name = "car_id")
    @ManyToOne
    private PKCar car;

    private Date checkinTime;
    private Date checkoutTime;

    @JoinColumn(name = "bill_id")
    @ManyToOne
    private PKBill bill;
}
