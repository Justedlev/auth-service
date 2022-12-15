package com.justedlev.auth.component;

import com.justedlev.auth.component.command.SendEmailCommand;

public interface MailComponent {
    void sendConfirmActivationMail(SendEmailCommand command);
}
