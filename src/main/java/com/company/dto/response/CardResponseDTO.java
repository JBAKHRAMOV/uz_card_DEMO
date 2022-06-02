package com.company.dto.response;

import com.company.dto.request.CardRequestDTO;
import com.company.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponseDTO extends CardRequestDTO {
    private String id;
    private String number;
    private LocalDateTime createdDate;
    private StatusEnum status;
    private LocalDate expiryDate;
    private ClientResponseDTO client;

}
