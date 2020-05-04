package org.fit.linevich.domain;

import lombok.Data;
import org.fit.linevich.converters.AnimalTypeConverter;
import org.fit.linevich.converters.ClimaticZoneConverter;
import org.fit.linevich.converters.GenderConverter;
import org.fit.linevich.converters.PhysStateConverter;
import org.fit.linevich.model.AnimalType;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Gender;
import org.fit.linevich.model.PhysState;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Collection;

@Data
@Entity
@Table(name = "animals", schema = "public", catalog = "bd_zoo")
public class AnimalEntity {
    @Id
    @GeneratedValue(generator = "animal_gen")
    @SequenceGenerator(name = "animal_gen", sequenceName = "animals_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "kind_animal", nullable = false, length = -1)
    private String kindAnimal;
    @Basic
    @Column(name = "type", nullable = false)
    @Convert(converter = AnimalTypeConverter.class)
    private AnimalType type;

    @Basic
    @Column(name = "climatic_habitat", nullable = false)
    @Convert(converter = ClimaticZoneConverter.class)
    private ClimaticZone climaticHabitat;

    @Basic
    @Column(name = "gender", nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Basic
    @Column(name = "physical_state", nullable = false)
    @Convert(converter = PhysStateConverter.class)
    private PhysState physicalState;

    @Basic
    @Column(name = "progeny", nullable = false)
    private Integer progeny;
    @Basic
    @Column(name = "birthday", nullable = false)
    private Date birthday;
    @Basic
    @Column(name = "departure_date", nullable = true)
    private Date departureDate;
    @Basic
    @Column(name = "departure_reason", nullable = true, length = -1)
    private String departureReason;
    @Basic
    @Column(name = "need_relocation", nullable = false)
    private Boolean needRelocation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<AccessAnimalsEntity> accessAnimalsById;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<AnimalCompatibilityEntity> animalCompatibilitiesById;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<AnimalReceiptEntity> animalReceiptsById;
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<CellsAnimalsEntity> cellsAnimalsById;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<EvenDayRationEntity> evenDayRationsById;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<IllnessAnimalsEntity> illnessAnimalsById;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "animal")
    private MedCardEntity medCardById;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<OddDayRationEntity> oddDayRationsById;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<ResponsibleAnimalsEntity> responsibleAnimalsById;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<VaccineEntity> vaccinesById;

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
