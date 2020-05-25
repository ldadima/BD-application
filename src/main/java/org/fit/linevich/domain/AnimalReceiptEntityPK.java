package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AnimalReceiptEntityPK implements Serializable {
    @Column(name = "zoo_id")
    private Integer zooId;
    @Column(name = "animal_id")
    private Integer animalId;
}
