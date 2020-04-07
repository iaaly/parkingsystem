package com.iaaly.parkingsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckinResponse extends BaseResponse {
    private Long ticketId;
    private String slotName;
}
