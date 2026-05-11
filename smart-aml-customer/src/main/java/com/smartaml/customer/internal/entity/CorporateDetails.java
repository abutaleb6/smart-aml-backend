package com.smartaml.customer.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "corporate_details")
@Getter
@Setter
@NoArgsConstructor
public class CorporateDetails {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "customer_id", columnDefinition = "uuid", nullable = false)
    private UUID customerId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "trade_license_cr_no")
    private String tradeLicenseNo;

    @Column(name = "email")
    private String email;
}
