package com.justedlev.auth.constant;

public final class MailBodyConstant {
    public static final String CONFIRM = """
            Welcome %s!
            Follow your individual link to confirm and activate your new account:
                        
            %s
                        
            Best regards,
            Justedlevhub
            """;

    private MailBodyConstant() {
        throw new IllegalStateException("Constants class");
    }
}
