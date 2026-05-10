package com.smartaml.user.web;

import com.smartaml.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    @GetMapping
    public ApiResponse<Object> list() { return ApiResponse.ok(List.of()); }
}
