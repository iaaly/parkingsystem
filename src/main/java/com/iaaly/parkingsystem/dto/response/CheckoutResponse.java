package com.iaaly.parkingsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckoutResponse extends BaseResponse {
    private String billRefNo;
    private BigDecimal billAmount;
    private String billCurrency;
}
