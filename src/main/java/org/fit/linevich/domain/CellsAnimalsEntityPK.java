package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
public class CellsAnimalsEntityPK implements Serializable {
    @Column(name = "cell_id", nullable = false)
    @Id
    private int cellId;
    @Id
    @Column(name = "animal_id")
    private int animalId;
}
