package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AnimalReceiptEntityPK implements Serializable {
    private int zooId;
    private int animalId;

    @Column(name = "zoo_id", nullable = false)
    @Id
    public int getZooId() {
        return zooId;
    }

    public void setZooId(int zooId) {
        this.zooId = zooId;
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
        AnimalReceiptEntityPK that = (AnimalReceiptEntityPK) o;
        return zooId == that.zooId &&
                animalId == that.animalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zooId, animalId);
    }
}
