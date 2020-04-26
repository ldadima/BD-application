package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccessAnimals {
    @NotNull
    private int employerId;
    @NotNull
    private int animalId;
}
