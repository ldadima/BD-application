package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class CellsAnimalsEntityPK implements Serializable {
    @Column(name = "cell_id", nullable = false)
    @Id
    private Integer cellId;
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
}
