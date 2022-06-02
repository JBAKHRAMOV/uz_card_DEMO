package com.company.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CardAssignRequestDTO {
    @NotBlank(message = "The phone should not be empty")
    private String phone;
}
