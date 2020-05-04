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
    private Integer progeny;
    @NotNull
    private Date birthday;
    private Date departureDate;
    private String departureReason;
    private Boolean needRelocation;

    public String getType() {
        return type.getName();
    }

    public void setType(String type) {
        this.type = AnimalType.findByName(type);
    }

    public String getClimaticHabitat() {
        return climaticHabitat.getName();
    }

    public void setClimaticHabitat(String climaticHabitat) {
        this.climaticHabitat = ClimaticZone.findByName(climaticHabitat);
    }

    public String getGender() {
        return gender.getName();
    }

    public void setGender(String gender) {
        this.gender = Gender.findByName(gender);
    }

    public String getPhysicalState() {
        return physicalState.getName();
    }

    public void setPhysicalState(String physicalState) {
        this.physicalState = PhysState.findByName(physicalState);
    }
}
