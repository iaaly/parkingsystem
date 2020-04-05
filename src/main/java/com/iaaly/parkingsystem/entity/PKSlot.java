package com.iaaly.parkingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * A parking slot entity, has a type and a floor, an indication if it's already occupied and by which car
 */
@Entity(name = "slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKSlot {

    @Id
    @GeneratedValue
    private long id;

    /**
     * The slot number, for example: `A1002`
     */
    @Column(unique = true)
    private String identifier;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private PKFloor floor;

    @ManyToOne
    @JoinColumn(name = "slot_type_id")
    private PKSlotType slotType;

    private boolean occupied;

    /**
     * The car currently occupying the parking slot, NULL if it's free
     */
    @ManyToOne
    @JoinColumn(name = "customer_car_id")
    private PKCar customerCar;
}
