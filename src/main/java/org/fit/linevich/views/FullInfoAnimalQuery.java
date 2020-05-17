package org.fit.linevich.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.fit.linevich.domain.IllnessEntity;
import org.fit.linevich.domain.MedCardEntity;
import org.fit.linevich.domain.VaccineEntity;
import org.fit.linevich.model.AnimalType;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Gender;
import org.fit.linevich.model.PhysState;

import java.util.Date;

@Data
@AllArgsConstructor
public class FullInfoAnimalQuery {
    private String name;
    private String kindAnimal;
    private AnimalType type;
    private ClimaticZone climaticHabitat;
    private Gender gender;
    private PhysState physicalState;
    private Integer progeny;
    private Date birthday;
    private Integer height;
    private Integer weight;
    private String vaccine;
    private String illness;


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
