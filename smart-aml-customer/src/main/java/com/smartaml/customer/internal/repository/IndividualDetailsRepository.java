package com.smartaml.customer.internal.repository;

import com.smartaml.customer.internal.entity.IndividualDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualDetailsRepository extends JpaRepository<IndividualDetails, UUID> {
}
