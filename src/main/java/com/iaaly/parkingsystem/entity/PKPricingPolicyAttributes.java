package com.iaaly.parkingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The entity holding the Pricing Policy attributes, such as hourly rate, fixed rate
 * In the future, this can be extended to include more attributes to be used in more complex billing calculations
 */
@Entity(name = "pricing_policy_attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKPricingPolicyAttributes {
    @Id
    @GeneratedValue
    private long id;
    private double fixedRate;
    private double hourlyRate;
}
