package com.company.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ClientRequestDTO {
    @NotBlank(message = "The name should not be empty")
    private String name;
    @NotBlank(message = "The surname should not be empty")
    private String surname;
    @NotBlank(message = "The Middle Name should not be empty")
    private String middleName;
    @NotBlank(message = "The phone should not be empty")
    private String phone;

    public ClientRequestDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
