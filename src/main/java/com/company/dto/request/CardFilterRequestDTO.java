package com.company.dto.request;

import com.company.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardFilterRequestDTO {
    private String client_id;
    private String cardNumber;
    private String cardId;
    private Long fromBalance;
    private Long toBalance;
    private String profileName;
    private StatusEnum status;
    private LocalDateTime createdDate;
}
