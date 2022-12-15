package com.justedlev.auth.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.auth.enumeration.UserStatusCode;
import com.justedlev.auth.model.LastHash;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UserStatusCode status;
    private String hashCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdAt;
    private List<LastHash> lastHashes;
    private Set<RoleResponse> roles;
    private AccountResponse account;
}
