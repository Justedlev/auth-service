package com.justedlev.auth.component.base;

import java.util.List;

public interface ResponseMapper<I, O> {
    O mapToResponse(I request);

    List<O> mapToResponse(List<I> requests);
}
