package com.iaaly.parkingsystem.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CheckinRequest {
    @NotBlank
    private String slotTypeKey;
    @NotBlank
    private String carPlateNumber;
}
