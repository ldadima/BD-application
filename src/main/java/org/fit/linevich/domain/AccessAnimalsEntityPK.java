package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AccessAnimalsEntityPK implements Serializable {
    private int employerId;
    private int animalId;

    @Column(name = "employer_id", nullable = false)
    @Id
    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    @Column(name = "animal_id", nullable = false)
    @Id
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessAnimalsEntityPK that = (AccessAnimalsEntityPK) o;
        return employerId == that.employerId &&
                animalId == that.animalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employerId, animalId);
    }
}
