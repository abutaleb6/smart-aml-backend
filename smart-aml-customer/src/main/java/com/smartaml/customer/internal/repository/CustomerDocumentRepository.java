package com.smartaml.customer.internal.repository;

import com.smartaml.customer.internal.entity.CustomerDocument;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, UUID> {
}
