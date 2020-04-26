package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnimalCompatibility {
    @NotNull
    private int animalId;
    @NotNull
    private String animalKind;
}
