package com.justedlev.auth.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justedlev.auth.model.converter.UpperCaseConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    @NotBlank(message = "Input role type cannot be empty.")
    @JsonDeserialize(converter = UpperCaseConverter.class)
    private String type;
    @NotBlank(message = "Input role group cannot be empty.")
    @JsonDeserialize(converter = UpperCaseConverter.class)
    private String group;
    private String about;
}
