package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class IllnessAnimalsEntityPK implements Serializable {
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
    @Id
    @Column(name = "illness_id")
    private Integer illnessId;
}
