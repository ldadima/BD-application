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
}
