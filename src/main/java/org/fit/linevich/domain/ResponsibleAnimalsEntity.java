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
@Table(name = "responsible_animals", schema = "public", catalog = "bd_zoo")
@IdClass(ResponsibleAnimalsEntityPK.class)
public class ResponsibleAnimalsEntity {
    private int employerId;
    private int animalId;
    private Date dateBegin;
    private Date dateEnd;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponsibleAnimalsEntity that = (ResponsibleAnimalsEntity) o;
        return employerId == that.employerId &&
                animalId == that.animalId &&
                Objects.equals(dateBegin, that.dateBegin) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employerId, animalId, dateBegin, dateEnd);
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
