package com.company.dto.response;

import com.company.dto.request.ClientRequestDTO;
import com.company.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDTO extends ClientRequestDTO {
    private String id;
    private StatusEnum status;
    private LocalDateTime createdDate;
    private String profileName;
}
