package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.AnimalType;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Gender;
import org.fit.linevich.model.PhysState;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Animal {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String kindAnimal;
    @NotNull
    private AnimalType type;
    @NotNull
    private ClimaticZone climaticHabitat;
    @NotNull
    private Gender gender;
    @NotNull
    private PhysState physicalState;
    @NotNull
    private Integer progeny;
    @NotNull
    private LocalDate birthday;
    private LocalDate departureDate;
    private String departureReason;
    @NotNull
    private Boolean needRelocation;
}
