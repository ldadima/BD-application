package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal_compatibility")
public class AnimalCompatibilityEntity {
    @EmbeddedId
    private AnimalCompatibilityEntityPK animalCompatibilityEntityPK;

    @MapsId("animalId")
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
}
