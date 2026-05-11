package com.smartaml.customer.internal.repository;

import com.smartaml.customer.internal.entity.CorporateDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateDetailsRepository extends JpaRepository<CorporateDetails, UUID> {
}
