package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card")
public class CardEntity extends BestEntity {
    @Column
    private String number;
    @Column
    private Long balance = 0l;
    @Column
    private String phone;

    @Column(name = "client_id")
    private String clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private ClientEntity client;

    @Column(name = "profile_name")
    private String profileName;
    @Column(name = "expiry_date")
    LocalDate expiryDate;
}
