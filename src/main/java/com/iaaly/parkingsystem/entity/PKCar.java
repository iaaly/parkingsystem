package com.iaaly.parkingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A customer car
 */
@Entity(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKCar {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String plateNumber;
}
