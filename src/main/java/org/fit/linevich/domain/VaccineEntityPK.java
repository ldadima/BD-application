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
public class VaccineEntityPK implements Serializable {
    @Column(name = "vaccine_id", nullable = false)
    private Integer vaccineId;
    @Column(name = "animal_id")
    private Integer animalId;
}
