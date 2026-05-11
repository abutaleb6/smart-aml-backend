package com.smartaml.customer.internal.repository;

import com.smartaml.customer.internal.entity.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByTenantId(UUID tenantId, Pageable pageable);
}
