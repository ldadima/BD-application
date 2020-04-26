package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AnimalCompatibilityEntityPK implements Serializable {
    private int animalId;
    private String animalKind;

    @Column(name = "animal_id", nullable = false)
    @Id
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Column(name = "animal_kind", nullable = false, length = -1)
    @Id
    public String getAnimalKind() {
        return animalKind;
    }

    public void setAnimalKind(String animalKind) {
        this.animalKind = animalKind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalCompatibilityEntityPK that = (AnimalCompatibilityEntityPK) o;
        return animalId == that.animalId &&
                Objects.equals(animalKind, that.animalKind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, animalKind);
    }
}
