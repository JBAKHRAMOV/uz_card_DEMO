package com.company.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransactionsRequestDTO {
    @NotBlank(message = "The Card Id should not be empty")
    private String fromCardId;
    @NotBlank(message = "The Card Id should not be empty")
    private String toCardId;
    private Long amount;
}
