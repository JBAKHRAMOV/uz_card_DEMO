package com.company.mapper;

import com.company.enums.StatusEnum;

import java.time.LocalDateTime;

public interface TransactionsMapper {
    String getT_id();

    Long getT_amount();

    StatusEnum getT_status();

    LocalDateTime getT_createdDate();

    String getFcr_id();

    String getFcr_number();

    String getFcr_phone();

    String getFcl_id();

    String getFcl_name();

    String getFcl_surname();

    String getTcr_id();

    String getTcr_number();

    String getTcr_phone();

    String getTcl_id();

    String getTcl_name();

    String getTcl_surname();

}
