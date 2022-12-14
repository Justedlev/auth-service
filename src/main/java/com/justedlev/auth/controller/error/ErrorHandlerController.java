package com.justedlev.auth.controller.error;

import com.justedlev.auth.model.response.ErrorDetailsResponse;
import com.justedlev.auth.model.response.ValidationErrorResponse;
import feign.FeignException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {IllegalArgumentException.class, EntityExistsException.class, IllegalStateException.class})
    public ResponseEntity<ErrorDetailsResponse> handleConflict(Exception ex,
                                                               WebRequest request) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        var errorDetails = ErrorDetailsResponse.builder()
                .details(request.getDescription(false))
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                              WebRequest request) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        var errorDetails = ErrorDetailsResponse.builder()
                .details(request.getDescription(false))
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<ErrorDetailsResponse> handleFeignException(FeignException ex,
                                                                     WebRequest request) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        var errorDetails = ErrorDetailsResponse.builder()
                .details(request.getDescription(false))
                .message(ex.getLocalizedMessage())
                .build();
        var status = ex.status() > 0 ? HttpStatus.valueOf(ex.status()) : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(errorDetails, status);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        var violations = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(current -> ValidationErrorResponse.Violation.builder()
                        .fieldName(current.getField())
                        .message(current.getDefaultMessage())
                        .build())
                .toList();
        var error = ValidationErrorResponse.builder()
                .violations(violations)
                .build();

        return new ResponseEntity<>(error, status);
    }
}
