package com.justedlev.auth.model.request;

import com.justedlev.auth.enumeration.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountModeRequest {
    private Collection<ModeType> fromModes;
    private ModeType toMode;
}
