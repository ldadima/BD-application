package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fit.linevich.converters_for_db.AnimalTypeConverter;
import org.fit.linevich.converters_for_db.ClimaticZoneConverter;
import org.fit.linevich.converters_for_db.GenderConverter;
import org.fit.linevich.converters_for_db.PhysStateConverter;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animals")
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
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<AccessAnimalsEntity> accessAnimalsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<AnimalCompatibilityEntity> animalCompatibilitiesById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<AnimalReceiptEntity> animalReceiptsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<CellsAnimalsEntity> cellsAnimalsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<EvenDayRationEntity> evenDayRationsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<IllnessAnimalsEntity> illnessAnimalsById;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "animal")
    private MedCardEntity medCardById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<OddDayRationEntity> oddDayRationsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<ResponsibleAnimalsEntity> responsibleAnimalsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "animalId")
    private Collection<VaccineEntity> vaccinesById;
}
