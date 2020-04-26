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
@Table(name = "access_animals", schema = "public", catalog = "bd_zoo")
@IdClass(AccessAnimalsEntityPK.class)
public class AccessAnimalsEntity {
    private int employerId;
    private int animalId;
    private EmployeeEntity employeesByEmployerId;
    private AnimalEntity animalsByAnimalId;

    @Id
    @Column(name = "employer_id", nullable = false)
    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    @Id
    @Column(name = "animal_id", nullable = false)
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
        AccessAnimalsEntity that = (AccessAnimalsEntity) o;
        return employerId == that.employerId &&
                animalId == that.animalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employerId, animalId);
    }

    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public EmployeeEntity getEmployeesByEmployerId() {
        return employeesByEmployerId;
    }

    public void setEmployeesByEmployerId(EmployeeEntity employeesByEmployerId) {
        this.employeesByEmployerId = employeesByEmployerId;
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
