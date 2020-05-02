package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "animal_compatibility", schema = "public", catalog = "bd_zoo")
@IdClass(AnimalCompatibilityEntityPK.class)
public class AnimalCompatibilityEntity {
    @Id
    @Column(name = "animal_kind", nullable = false, length = -1)
    private String animalKind;
    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
}
