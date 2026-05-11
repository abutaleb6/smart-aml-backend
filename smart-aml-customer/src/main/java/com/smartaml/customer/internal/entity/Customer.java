package com.smartaml.customer.internal.entity;

import com.smartaml.infrastructure.persistence.BaseEntity;
import com.smartaml.shared.enums.CustomerStatus;
import com.smartaml.shared.enums.CustomerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CustomerStatus status = CustomerStatus.DRAFT;

    @Column(name = "risk_score")
    private Double riskScore;

    @Column(name = "created_by", columnDefinition = "uuid")
    private UUID createdBy;

}
