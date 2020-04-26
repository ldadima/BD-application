package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.AnimalType;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Gender;
import org.fit.linevich.model.PhysState;

import javax.validation.constraints.NotNull;
import java.sql.Date;

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
    private int progeny;
    @NotNull
    private Date birthday;
    private Date departureDate;
    private String departureReason;
    private boolean needRelocation;

    public String getType() {
        return type.getType();
    }

    public String getPhysicalState() {
        return physicalState.getState();
    }

    public String getGender() {
        return gender.getGender();
    }

    public String getClimaticHabitat() {
        return climaticHabitat.getZone();
    }
}
