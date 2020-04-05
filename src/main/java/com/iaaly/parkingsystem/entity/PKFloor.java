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
 * A Parking floor
 */
@Entity(name = "floors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKFloor {
    @Id
    @GeneratedValue
    private long id;

    /**
     * e.g. P1 or P2...
     */
    @Column(unique = true)
    private String name;
}
