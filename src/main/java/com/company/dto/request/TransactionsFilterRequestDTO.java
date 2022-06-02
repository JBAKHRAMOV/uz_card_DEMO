package com.company.dto.request;

import com.company.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionsFilterRequestDTO {
    private String id;
    private Long fromAmount;
    private Long toAmount;
    private LocalDate toDate;
    private LocalDate fromDate;
    private StatusEnum status;
}
