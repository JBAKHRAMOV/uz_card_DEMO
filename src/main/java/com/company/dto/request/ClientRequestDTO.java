package com.company.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
public class ClientRequestDTO {
    @NotBlank(message = "The name should not be empty")
    private String name;
    @NotBlank(message = "The surname should not be empty")
    private String surname;
    @NotBlank(message = "The Middle Name should not be empty")
    private String middleName;
    @NotBlank(message = "The phone should not be empty")
    private String phone;
}
