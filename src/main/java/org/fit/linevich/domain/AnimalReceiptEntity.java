package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal_receipt")
public class AnimalReceiptEntity {
    @EmbeddedId
    private AnimalReceiptEntityPK animalReceiptEntityPK;



    @Basic
    @Column(name = "date_receipt", nullable = false)
    private Date dateReceipt;
    @MapsId("zooId")
    @ManyToOne
    @JoinColumn(name = "zoo_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ZooEntity zooId;
    @MapsId("animalId")
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
}
