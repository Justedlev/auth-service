package com.justedlev.auth.repository.entity.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LastHash implements Serializable {
    private String hashCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Builder.Default
    private Date time = new Date();
}
