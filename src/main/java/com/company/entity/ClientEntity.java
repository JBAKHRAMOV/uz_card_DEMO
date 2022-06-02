package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "client")
public class ClientEntity extends BestEntity {
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String middleName;
    @Column
    private String phone;
    @Column(name = "profile_name")
    private String profileName;


}
