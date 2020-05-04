package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class EvenDayRationEntityPK implements Serializable {
    @Column(name = "season", nullable = false)
    @Id
    private String season;
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
}
