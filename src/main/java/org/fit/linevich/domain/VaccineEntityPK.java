package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class VaccineEntityPK implements Serializable {
    private int animalId;
    private int vaccineId;

    @Column(name = "animal_id", nullable = false)
    @Id
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Column(name = "vaccine_id", nullable = false)
    @Id
    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccineEntityPK that = (VaccineEntityPK) o;
        return animalId == that.animalId &&
                vaccineId == that.vaccineId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, vaccineId);
    }
}
