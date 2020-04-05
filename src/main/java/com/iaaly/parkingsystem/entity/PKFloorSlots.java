package com.iaaly.parkingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The definition of how many slots per type for a certain floor
 */
@Entity(name = "floor_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKFloorSlots {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private PKFloor floor;

    @ManyToOne
    @JoinColumn(name = "slot_type_id")
    private PKSlotType slotType;

    private Integer nbSlots;

}
