package com.smartaml.customer.web;

import com.smartaml.customer.api.CustomerApi;
import com.smartaml.customer.api.dto.CreateCustomerRequest;
import com.smartaml.customer.api.dto.CustomerSummary;
import com.smartaml.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerApi customerApi;

    public CustomerController(CustomerApi customerApi) { this.customerApi = customerApi; }

    @PostMapping
    public ApiResponse<UUID> create(@RequestBody CreateCustomerRequest req, @RequestAttribute(required = false) UUID userId) {
        UUID id = customerApi.createCustomer(req, userId);
        return ApiResponse.created(id);
    }

    @PostMapping("/{id}/submit")
    public ApiResponse<String> submit(@PathVariable UUID id, @RequestAttribute(required = false) UUID userId) {
        customerApi.submitCustomer(id, userId);
        return ApiResponse.message("Submitted");
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerSummary> get(@PathVariable UUID id) {
        CustomerSummary s = customerApi.getCustomer(id);
        return ApiResponse.ok(s);
    }

    @GetMapping
    public ApiResponse<List<CustomerSummary>> list(@RequestParam UUID tenantId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.ok(customerApi.listByTenant(tenantId, page, size));
    }
}
