package org.fit.linevich.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "vaccines", schema = "public", catalog = "bd_zoo")
@IdClass(VaccineEntityPK.class)
public class VaccineEntity {
    private int animalId;
    private int vaccineId;
    private String medicineName;
    private int dose;
    private Date dateVaccine;
    private AnimalEntity animalsByAnimalId;

    @Id
    @Column(name = "animal_id", nullable = false)
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Id
    @Column(name = "vaccine_id", nullable = false)
    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    @Basic
    @Column(name = "medicine_name", nullable = false, length = -1)
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    @Basic
    @Column(name = "dose", nullable = false)
    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    @Basic
    @Column(name = "date_vaccine", nullable = false)
    public Date getDateVaccine() {
        return dateVaccine;
    }

    public void setDateVaccine(Date dateVaccine) {
        this.dateVaccine = dateVaccine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccineEntity that = (VaccineEntity) o;
        return animalId == that.animalId &&
                vaccineId == that.vaccineId &&
                dose == that.dose &&
                Objects.equals(medicineName, that.medicineName) &&
                Objects.equals(dateVaccine, that.dateVaccine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, vaccineId, medicineName, dose, dateVaccine);
    }

    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public AnimalEntity getAnimalsByAnimalId() {
        return animalsByAnimalId;
    }

    public void setAnimalsByAnimalId(AnimalEntity animalsByAnimalId) {
        this.animalsByAnimalId = animalsByAnimalId;
    }
}
