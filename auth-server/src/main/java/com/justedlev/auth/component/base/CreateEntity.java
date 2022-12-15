package com.justedlev.auth.component.base;

public interface CreateEntity<I, O> {
    O create(I request);
}
