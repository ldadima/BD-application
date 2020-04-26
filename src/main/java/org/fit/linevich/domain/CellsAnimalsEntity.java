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
@Table(name = "cells_animals", schema = "public", catalog = "bd_zoo")
@IdClass(CellsAnimalsEntityPK.class)
public class CellsAnimalsEntity {
    private Integer cellId;
    private int animalId;
    private Date dateBegin;
    private Date dateEnd;
    private boolean heating;
    private AnimalEntity animalsByAnimalId;

    @Id
    @Column(name = "cell_id", nullable = false)
    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    @Id
    @Column(name = "animal_id", nullable = false)
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Basic
    @Column(name = "date_begin", nullable = false)
    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    @Basic
    @Column(name = "date_end", nullable = true)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Basic
    @Column(name = "heating", nullable = false)
    public boolean isHeating() {
        return heating;
    }

    public void setHeating(boolean heating) {
        this.heating = heating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellsAnimalsEntity that = (CellsAnimalsEntity) o;
        return cellId == that.cellId &&
                animalId == that.animalId &&
                heating == that.heating &&
                Objects.equals(dateBegin, that.dateBegin) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellId, animalId, dateBegin, dateEnd, heating);
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
