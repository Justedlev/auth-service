package com.justedlev.auth.repository.custom.filter;

import com.justedlev.auth.enumeration.UserStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilter {
    private Collection<String> ids;
    private Collection<String> usernames;
    private Collection<UserStatusCode> statuses;
}
