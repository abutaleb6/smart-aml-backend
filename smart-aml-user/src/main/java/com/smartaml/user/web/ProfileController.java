package com.smartaml.user.web;

import com.smartaml.shared.response.ApiResponse;
import com.smartaml.user.api.UserApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final UserApi userApi;
    public ProfileController(UserApi userApi) { this.userApi = userApi; }

    @GetMapping
    public ApiResponse<Object> me() {
        // Return placeholder profile + tenant summary
        return ApiResponse.ok(Object.class);
    }
}
