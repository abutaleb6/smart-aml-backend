package com.smartaml.customer.internal.service;

import com.smartaml.customer.api.CustomerApi;
import com.smartaml.customer.api.dto.CreateCustomerRequest;
import com.smartaml.customer.api.dto.CustomerSummary;
import com.smartaml.customer.internal.entity.Customer;
import com.smartaml.customer.internal.repository.CustomerRepository;
import com.smartaml.shared.event.CustomerSubmittedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService implements CustomerApi {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher publisher;

    public CustomerService(CustomerRepository customerRepository, ApplicationEventPublisher publisher) {
        this.customerRepository = customerRepository;
        this.publisher = publisher;
    }

    @Override
    public UUID createCustomer(CreateCustomerRequest req, UUID createdBy) {
        Customer c = new Customer();
        c.setCustomerType(req.customerType());
        c.setStatus(com.smartaml.shared.enums.CustomerStatus.DRAFT);
        c.setCreatedBy(createdBy);
        customerRepository.save(c);
        return c.getId();
    }

    @Override
    public void submitCustomer(UUID customerId, UUID submittedBy) {
        var opt = customerRepository.findById(customerId);
        if (opt.isEmpty()) throw new IllegalArgumentException("Customer not found");
        Customer c = opt.get();
        c.setStatus(com.smartaml.shared.enums.CustomerStatus.SUBMITTED);
        customerRepository.save(c);
        // publish event for screening & audit
        publisher.publishEvent(new CustomerSubmittedEvent(this, c.getTenantId(), submittedBy, c.getId(), c.getCustomerType()));
    }

    @Override
    public CustomerSummary getCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(c -> new CustomerSummary(c.getId(), c.getTenantId(), c.getCustomerType(), c.getStatus(), c.getRiskScore() == null ? 0.0 : c.getRiskScore()))
                .orElse(null);
    }

    @Override
    public List<CustomerSummary> listByTenant(UUID tenantId, int page, int size) {
        var list = customerRepository.findByTenantId(tenantId, PageRequest.of(page, size));
        return list.stream().map(c -> new CustomerSummary(c.getId(), c.getTenantId(), c.getCustomerType(), c.getStatus(), c.getRiskScore() == null ? 0.0 : c.getRiskScore())).collect(Collectors.toList());
    }
}
