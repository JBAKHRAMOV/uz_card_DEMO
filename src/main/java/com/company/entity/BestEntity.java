package com.company.entity;

import com.company.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter
public class BestEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    protected String id;

    @Column(name = "created_date")
    protected LocalDateTime createdDate = LocalDateTime.now();
    @Column
    @Enumerated(EnumType.STRING)
    protected StatusEnum status;
}
