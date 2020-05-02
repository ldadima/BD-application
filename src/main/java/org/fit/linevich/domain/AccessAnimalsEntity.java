package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "access_animals", schema = "public", catalog = "bd_zoo")
@IdClass(AccessAnimalsEntityPK.class)
public class AccessAnimalsEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private EmployeeEntity employerId;
    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
}
