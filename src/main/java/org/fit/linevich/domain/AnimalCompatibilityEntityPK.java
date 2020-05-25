package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AnimalCompatibilityEntityPK implements Serializable {
    @Column(name = "animal_kind")
    private String animalKind;
    @Column(name = "animal_id")
    private Integer animalId;
}
