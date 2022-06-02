package com.company.dto.response;

import com.company.dto.request.ClientRequestDTO;
import com.company.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDTO  {
    private String id;
    private String name;
    private String surname;
    private String middleName;
    private String phone;
    private String profileName;
    private StatusEnum status;
    private LocalDateTime createdDate;

    public ClientResponseDTO(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
