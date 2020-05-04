package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class AnimalReceiptEntityPK implements Serializable {
    @Id
    @Column(name = "zoo_id")
    private Integer zooId;
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
}
