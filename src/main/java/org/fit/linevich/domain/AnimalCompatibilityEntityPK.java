package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class AnimalCompatibilityEntityPK implements Serializable {
    @Id
    @Column(name = "animal_kind")
    private String animalKind;
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
}
