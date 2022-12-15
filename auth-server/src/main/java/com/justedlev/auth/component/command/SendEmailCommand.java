package com.justedlev.auth.component.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailCommand {
    private String recipient;
    private String userName;
    private String activationCode;
}
