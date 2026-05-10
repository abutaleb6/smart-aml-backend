package com.smartaml.user.web;

import com.smartaml.shared.response.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @GetMapping
    public ApiResponse<Object> list() { return ApiResponse.ok(List.of()); }

}
