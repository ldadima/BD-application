package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class IllnessAnimalsEntityPK implements Serializable {
    private int animalId;
    private int illnessId;

    @Column(name = "animal_id", nullable = false)
    @Id
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Column(name = "illness_id", nullable = false)
    @Id
    public int getIllnessId() {
        return illnessId;
    }

    public void setIllnessId(int illnessId) {
        this.illnessId = illnessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IllnessAnimalsEntityPK that = (IllnessAnimalsEntityPK) o;
        return animalId == that.animalId &&
                illnessId == that.illnessId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, illnessId);
    }
}
