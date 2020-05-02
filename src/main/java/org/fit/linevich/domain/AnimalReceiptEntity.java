package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name = "animal_receipt", schema = "public", catalog = "bd_zoo")
@IdClass(AnimalReceiptEntityPK.class)
public class AnimalReceiptEntity {
    @Basic
    @Column(name = "date_receipt", nullable = false)
    private Date dateReceipt;
    @Id
    @ManyToOne
    @JoinColumn(name = "zoo_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ZooEntity zooId;
    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
}
