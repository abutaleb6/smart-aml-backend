package com.smartaml.user.internal.entity;

import com.smartaml.infrastructure.persistence.BaseEntity;
import com.smartaml.shared.enums.TenantStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
public class Tenant extends BaseEntity {

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_email", nullable = false)
    private String companyEmail;

    @Column(name = "status")
    private TenantStatus status;

    @Column(name = "total_screenings_quota")
    private int totalScreeningsQuota;

    @Column(name = "used_screenings")
    private int usedScreenings;
}
