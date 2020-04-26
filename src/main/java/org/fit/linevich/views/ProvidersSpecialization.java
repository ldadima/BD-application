package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProvidersSpecialization {
    @NotNull
    private int feedId;
    @NotNull
    private int providerId;
}
