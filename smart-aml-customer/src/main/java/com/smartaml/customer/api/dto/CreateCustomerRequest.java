package com.smartaml.customer.api.dto;

import com.smartaml.shared.enums.CustomerType;

public record CreateCustomerRequest(CustomerType customerType, String displayName) {}
