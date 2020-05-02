package org.fit.linevich.domain;

import lombok.Data;
import org.fit.linevich.model.Season;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class EvenDayRationEntityPK implements Serializable {
    @Column(name = "season", nullable = false)
    @Id
    private Season season;
    @Id
    @Column(name = "animal_id")
    private int animalId;
}
