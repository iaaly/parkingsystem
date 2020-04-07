package com.iaaly.parkingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * A Parking Slot type, defining the category of acceptable cars
 */
@Entity(name = "slot_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKSlotType {

    @Id
    @GeneratedValue
    private long id;

    /**
     * The key type to reference the parking slot, for example if this key is `A`, the parking slots numbering will
     * start with `A` as a prefix. e.g. A0021
     */
    @Column(length = 1, unique = true)
    @Size(min = 1, max = 1)
    private String key;

    private String description;

    @ManyToOne
    @JoinColumn(name = "pricing_policy_attributes_id")
    private PKPricingPolicyAttributes pricingPolicyAttributes;
}
