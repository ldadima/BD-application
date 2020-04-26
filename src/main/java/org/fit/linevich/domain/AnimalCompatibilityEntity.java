package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "animal_compatibility", schema = "public", catalog = "bd_zoo")
@IdClass(AnimalCompatibilityEntityPK.class)
public class AnimalCompatibilityEntity {
    private int animalId;
    private String animalKind;
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
    @Column(name = "animal_kind", nullable = false, length = -1)
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
        AnimalCompatibilityEntity that = (AnimalCompatibilityEntity) o;
        return animalId == that.animalId &&
                Objects.equals(animalKind, that.animalKind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, animalKind);
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
