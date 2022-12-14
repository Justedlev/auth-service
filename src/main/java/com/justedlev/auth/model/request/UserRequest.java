package com.justedlev.auth.model.request;

import com.justedlev.auth.constant.RoleTypeConstant;
import lombok.*;

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
    private String roleType = RoleTypeConstant.USER;
}
