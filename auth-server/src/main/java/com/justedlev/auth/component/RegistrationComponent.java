package com.justedlev.auth.component;

import com.justedlev.auth.model.request.RegistrationRequest;
import com.justedlev.auth.model.response.ReportResponse;

public interface RegistrationComponent {
    ReportResponse registration(RegistrationRequest registrationRequest);
}