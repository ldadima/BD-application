package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class VaccineEntityPK implements Serializable {
    @Column(name = "vaccine_id", nullable = false)
    @Id
    private int vaccineId;
    @Id
    @Column(name = "animal_id")
    private int animalId;
}
