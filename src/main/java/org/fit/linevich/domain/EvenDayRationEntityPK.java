package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fit.linevich.converters_for_db.SeasonConverter;
import org.fit.linevich.model.Season;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvenDayRationEntityPK implements Serializable {
    @Column(name = "season", nullable = false)
    @Convert(converter = SeasonConverter.class)
    private Season season;
    @Column(name = "animal_id")
    private Integer animalId;
}
