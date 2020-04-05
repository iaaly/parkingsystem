package com.iaaly.parkingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PKBill {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String refNo;

    private BigDecimal amount;

    @Column(length = 3)
    @Size(min = 3, max = 3)
    private String currency;

    private Date date;
}
