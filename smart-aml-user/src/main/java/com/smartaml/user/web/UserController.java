package com.smartaml.user.web;

import com.smartaml.shared.response.ApiResponse;
import com.smartaml.user.api.UserApi;
import com.smartaml.user.api.dto.UserSummary;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserApi userApi;

    public UserController(UserApi userApi) {
        this.userApi = userApi;
    }

    @GetMapping
    @PreAuthorize("@amlPerm.hasPermission(authentication,null,'user_management:VIEW')")
    public ApiResponse<Page<UserSummary>> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        // Placeholder: return empty page
        return ApiResponse.ok(new PageImpl<>(List.of()));
    }

    @PostMapping
    @PreAuthorize("@amlPerm.hasPermission(authentication,null,'user_management:CREATE')")
    public ApiResponse<UserSummary> create(@RequestBody Object req) {
        // Delegate to userApi.createUser
        UUID id = userApi.createUser(req, null);
        return ApiResponse.created(new UserSummary(id, null, "", "", null, false, List.of()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@amlPerm.hasPermission(authentication,null,'user_management:VIEW')")
    public ApiResponse<UserSummary> get(@PathVariable UUID id) {
        UserSummary u = userApi.findById(id);
        return ApiResponse.ok(u);
    }
}
