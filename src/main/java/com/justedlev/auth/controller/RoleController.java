package com.justedlev.auth.controller;

import com.justedlev.auth.constant.EndpointConstant;
import com.justedlev.auth.model.request.RoleRequest;
import com.justedlev.auth.model.response.ReportResponse;
import com.justedlev.auth.model.response.RoleResponse;
import com.justedlev.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EndpointConstant.ROLE)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping(value = EndpointConstant.PAGE)
    public ResponseEntity<List<RoleResponse>> getPage(@RequestParam(required = false) String group) {
        return Optional.ofNullable(group)
                .map(roleService::getAllByRoleGroup)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(roleService.getAll()));
    }

    @GetMapping(value = EndpointConstant.ROLE_TYPE)
    public ResponseEntity<RoleResponse> getRole(@Valid
                                                @PathVariable
                                                @NotBlank(message = "Role type cannot be empty")
                                                String type) {
        var response = this.roleService.get(type);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = EndpointConstant.ADD)
    public ResponseEntity<ReportResponse> addRole(@Valid @RequestBody RoleRequest request) {
        var response = this.roleService.add(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = EndpointConstant.ROLE_TYPE_UPDATE)
    public ResponseEntity<ReportResponse> updateRole(@Valid @RequestBody RoleRequest request) {
        var response = roleService.edit(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = EndpointConstant.ROLE_TYPE_DELETE)
    public ResponseEntity<ReportResponse> deleteRole(@PathVariable
                                                     @Valid
                                                     @NotBlank(message = "Role type cannot be empty")
                                                     String type) {
        var body = this.roleService.delete(type);

        return ResponseEntity.ok(body);
    }
}
