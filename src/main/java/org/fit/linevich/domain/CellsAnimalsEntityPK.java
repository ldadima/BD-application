package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellsAnimalsEntityPK implements Serializable {
    @Column(name = "cell_id", nullable = false)
    @Id
    private Integer cellId;
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
}
