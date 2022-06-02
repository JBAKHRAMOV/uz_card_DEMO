package com.company.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CardRequestDTO {
    private Long balance;
    @NotBlank(message = "The phone should not be empty")
    private String clientId;
}
