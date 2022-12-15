package com.justedlev.auth.component.command;

import java.time.Duration;
import java.util.Collection;

import com.justedlev.auth.enumeration.ModeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountModeCommand {
    private Collection<ModeType> fromModes;
    private ModeType toMode;
    private Duration duration;
}
