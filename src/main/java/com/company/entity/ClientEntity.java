package com.company.entity;

import com.company.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

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
