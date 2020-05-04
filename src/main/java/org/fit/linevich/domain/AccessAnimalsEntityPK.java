package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class AccessAnimalsEntityPK implements Serializable {
    @Id
    @Column(name = "employer_id")
    private Integer employerId;
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
}
