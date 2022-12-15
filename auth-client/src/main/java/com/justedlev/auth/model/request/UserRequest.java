package com.justedlev.auth.model.request;

import com.justedlev.auth.enumeration.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Input username cannot be empty.")
    private String username;
    @NotBlank(message = "Input password cannot be empty.")
    private String password;
    @Builder.Default
    private String roleType = RoleType.USER.getType();
}
