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
@Table(name = "illness_animals", schema = "public", catalog = "bd_zoo")
@IdClass(IllnessAnimalsEntityPK.class)
public class IllnessAnimalsEntity {
    private int animalId;
    private int illnessId;
    private Date dateBegin;
    private Date dateEnd;
    private AnimalEntity animalsByAnimalId;
    private IllnessEntity illnessesByIllnessId;

    @Id
    @Column(name = "animal_id", nullable = false)
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Id
    @Column(name = "illness_id", nullable = false)
    public int getIllnessId() {
        return illnessId;
    }

    public void setIllnessId(int illnessId) {
        this.illnessId = illnessId;
    }

    @Basic
    @Column(name = "date_begin", nullable = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IllnessAnimalsEntity that = (IllnessAnimalsEntity) o;
        return animalId == that.animalId &&
                illnessId == that.illnessId &&
                Objects.equals(dateBegin, that.dateBegin) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, illnessId, dateBegin, dateEnd);
    }

    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public AnimalEntity getAnimalsByAnimalId() {
        return animalsByAnimalId;
    }

    public void setAnimalsByAnimalId(AnimalEntity animalsByAnimalId) {
        this.animalsByAnimalId = animalsByAnimalId;
    }

    @ManyToOne
    @JoinColumn(name = "illness_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public IllnessEntity getIllnessesByIllnessId() {
        return illnessesByIllnessId;
    }

    public void setIllnessesByIllnessId(IllnessEntity illnessesByIllnessId) {
        this.illnessesByIllnessId = illnessesByIllnessId;
    }
}
