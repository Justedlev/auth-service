package com.justedlev.auth.component.base;

public interface UpdateEntity<I, O> {
    O update(O entity, I request);
}
