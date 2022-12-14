package com.justedlev.auth.repository.entity.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justedlev.auth.enumeration.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mode implements Serializable {
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ModeType type = ModeType.OFFLINE;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp time = new Timestamp(System.currentTimeMillis());

    public void setType(ModeType type) {
        this.type = type;
        this.time = new Timestamp(System.currentTimeMillis());
    }
}
