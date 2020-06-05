package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CellsAnimalsEntityPK implements Serializable {
    @Column(name = "cell_id", nullable = false)
    private Integer cellId;
    @Column(name = "animal_id")
    private Integer animalId;
}
