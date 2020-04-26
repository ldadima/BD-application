package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CellsAnimalsEntityPK implements Serializable {
    private int cellId;
    private int animalId;

    @Column(name = "cell_id", nullable = false)
    @Id
    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
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
        CellsAnimalsEntityPK that = (CellsAnimalsEntityPK) o;
        return cellId == that.cellId &&
                animalId == that.animalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellId, animalId);
    }
}
