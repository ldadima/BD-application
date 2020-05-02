package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class AccessAnimalsEntityPK implements Serializable {
    @Id
    @Column(name = "employer_id")
    private int employerId;
    @Id
    @Column(name = "animal_id")
    private int animalId;
}
