package com.company.dto.response;

import com.company.dto.request.TransactionsRequestDTO;
import com.company.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsResponseDTO extends TransactionsRequestDTO {
    private String id;
    private LocalDateTime createdDate;
    private StatusEnum status;
    private CardResponseDTO fromCard;
    private CardResponseDTO toCard;

}
