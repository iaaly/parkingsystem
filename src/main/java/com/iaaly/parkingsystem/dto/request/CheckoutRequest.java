package com.iaaly.parkingsystem.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutRequest {
    @NotNull
    private Long ticketId;
}
