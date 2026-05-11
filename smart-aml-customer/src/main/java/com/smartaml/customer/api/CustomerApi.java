package com.smartaml.customer.api;

import com.smartaml.customer.api.dto.CreateCustomerRequest;
import com.smartaml.customer.api.dto.CustomerSummary;
import java.util.List;
import java.util.UUID;

public interface CustomerApi {
    UUID createCustomer(CreateCustomerRequest req, UUID createdBy);
    void submitCustomer(UUID customerId, UUID submittedBy);
    CustomerSummary getCustomer(UUID customerId);
    List<CustomerSummary> listByTenant(UUID tenantId, int page, int size);
}
