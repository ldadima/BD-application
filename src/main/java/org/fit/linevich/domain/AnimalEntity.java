package org.fit.linevich.domain;

import io.swagger.models.auth.In;
import org.fit.linevich.model.AnimalType;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Gender;
import org.fit.linevich.model.PhysState;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "animals", schema = "public", catalog = "bd_zoo")
public class AnimalEntity {
    private Integer id;
    private String name;
    private String kindAnimal;
    private AnimalType type;
    private ClimaticZone climaticHabitat;
    private Gender gender;
    private PhysState physicalState;
    private int progeny;
    private Date birthday;
    private Date departureDate;
    private String departureReason;
    private boolean needRelocation;
    private Collection<AccessAnimalsEntity> accessAnimalsById;
    private Collection<AnimalCompatibilityEntity> animalCompatibilitiesById;
    private Collection<AnimalReceiptEntity> animalReceiptsById;
    private Collection<CellsAnimalsEntity> cellsAnimalsById;
    private Collection<EvenDayRationEntity> evenDayRationsById;
    private Collection<IllnessAnimalsEntity> illnessAnimalsById;
    private MedCardEntity medCardById;
    private Collection<OddDayRationEntity> oddDayRationsById;
    private Collection<ResponsibleAnimalsEntity> responsibleAnimalsById;
    private Collection<VaccineEntity> vaccinesById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "kind_animal", nullable = false, length = -1)
    public String getKindAnimal() {
        return kindAnimal;
    }

    public void setKindAnimal(String kindAnimal) {
        this.kindAnimal = kindAnimal;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public String getType() {
        return type.getType();
    }

    public void setType(String type) {
        this.type = AnimalType.findByName(type);
    }

    @Basic
    @Column(name = "climatic_habitat", nullable = false)
    public String getClimaticHabitat() {
        return climaticHabitat.getZone();
    }

    public void setClimaticHabitat(String climaticHabitat) {
        this.climaticHabitat = ClimaticZone.findByName(climaticHabitat);
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public String getGender() {
        return gender.getGender();
    }

    public void setGender(String gender) {
        this.gender = Gender.findByName(gender);
    }

    @Basic
    @Column(name = "physical_state", nullable = false)
    public String getPhysicalState() {
        return physicalState.getState();
    }

    public void setPhysicalState(String physicalState) {
        this.physicalState = PhysState.findByName(physicalState);
    }

    @Basic
    @Column(name = "progeny", nullable = false)
    public int getProgeny() {
        return progeny;
    }

    public void setProgeny(int progeny) {
        this.progeny = progeny;
    }

    @Basic
    @Column(name = "birthday", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "departure_date", nullable = true)
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @Basic
    @Column(name = "departure_reason", nullable = true, length = -1)
    public String getDepartureReason() {
        return departureReason;
    }

    public void setDepartureReason(String departureReason) {
        this.departureReason = departureReason;
    }

    @Basic
    @Column(name = "need_relocation", nullable = false)
    public boolean isNeedRelocation() {
        return needRelocation;
    }

    public void setNeedRelocation(boolean needRelocation) {
        this.needRelocation = needRelocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalEntity that = (AnimalEntity) o;
        return id == that.id &&
                progeny == that.progeny &&
                needRelocation == that.needRelocation &&
                Objects.equals(name, that.name) &&
                Objects.equals(kindAnimal, that.kindAnimal) &&
                Objects.equals(type, that.type) &&
                Objects.equals(climaticHabitat, that.climaticHabitat) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(physicalState, that.physicalState) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(departureDate, that.departureDate) &&
                Objects.equals(departureReason, that.departureReason);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, name, kindAnimal, type, climaticHabitat, gender, physicalState, progeny, birthday,
                        departureDate,
                        departureReason, needRelocation);
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<AccessAnimalsEntity> getAccessAnimalsById() {
        return accessAnimalsById;
    }

    public void setAccessAnimalsById(Collection<AccessAnimalsEntity> accessAnimalsById) {
        this.accessAnimalsById = accessAnimalsById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<AnimalCompatibilityEntity> getAnimalCompatibilitiesById() {
        return animalCompatibilitiesById;
    }

    public void setAnimalCompatibilitiesById(
            Collection<AnimalCompatibilityEntity> animalCompatibilitiesById) {
        this.animalCompatibilitiesById = animalCompatibilitiesById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<AnimalReceiptEntity> getAnimalReceiptsById() {
        return animalReceiptsById;
    }

    public void setAnimalReceiptsById(Collection<AnimalReceiptEntity> animalReceiptsById) {
        this.animalReceiptsById = animalReceiptsById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<CellsAnimalsEntity> getCellsAnimalsById() {
        return cellsAnimalsById;
    }

    public void setCellsAnimalsById(Collection<CellsAnimalsEntity> cellsAnimalsById) {
        this.cellsAnimalsById = cellsAnimalsById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<EvenDayRationEntity> getEvenDayRationsById() {
        return evenDayRationsById;
    }

    public void setEvenDayRationsById(Collection<EvenDayRationEntity> evenDayRationsById) {
        this.evenDayRationsById = evenDayRationsById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<IllnessAnimalsEntity> getIllnessAnimalsById() {
        return illnessAnimalsById;
    }

    public void setIllnessAnimalsById(Collection<IllnessAnimalsEntity> illnessAnimalsById) {
        this.illnessAnimalsById = illnessAnimalsById;
    }

    @OneToOne(mappedBy = "animalsByAnimalId")
    public MedCardEntity getMedCardById() {
        return medCardById;
    }

    public void setMedCardById(MedCardEntity medCardById) {
        this.medCardById = medCardById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<OddDayRationEntity> getOddDayRationsById() {
        return oddDayRationsById;
    }

    public void setOddDayRationsById(Collection<OddDayRationEntity> oddDayRationsById) {
        this.oddDayRationsById = oddDayRationsById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<ResponsibleAnimalsEntity> getResponsibleAnimalsById() {
        return responsibleAnimalsById;
    }

    public void setResponsibleAnimalsById(
            Collection<ResponsibleAnimalsEntity> responsibleAnimalsById) {
        this.responsibleAnimalsById = responsibleAnimalsById;
    }

    @OneToMany(mappedBy = "animalsByAnimalId")
    public Collection<VaccineEntity> getVaccinesById() {
        return vaccinesById;
    }

    public void setVaccinesById(Collection<VaccineEntity> vaccinesById) {
        this.vaccinesById = vaccinesById;
    }
}
