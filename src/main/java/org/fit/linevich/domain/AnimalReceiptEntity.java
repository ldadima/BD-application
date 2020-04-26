package org.fit.linevich.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "animal_receipt", schema = "public", catalog = "bd_zoo")
@IdClass(AnimalReceiptEntityPK.class)
public class AnimalReceiptEntity {
    private int zooId;
    private int animalId;
    private Date dateReceipt;
    private ZooEntity zoosByZooId;
    private AnimalEntity animalsByAnimalId;

    @Id
    @Column(name = "zoo_id", nullable = false)
    public int getZooId() {
        return zooId;
    }

    public void setZooId(int zooId) {
        this.zooId = zooId;
    }

    @Id
    @Column(name = "animal_id", nullable = false)
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Basic
    @Column(name = "date_receipt", nullable = false)
    public Date getDateReceipt() {
        return dateReceipt;
    }

    public void setDateReceipt(Date dateReceipt) {
        this.dateReceipt = dateReceipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalReceiptEntity that = (AnimalReceiptEntity) o;
        return zooId == that.zooId &&
                animalId == that.animalId &&
                Objects.equals(dateReceipt, that.dateReceipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zooId, animalId, dateReceipt);
    }

    @ManyToOne
    @JoinColumn(name = "zoo_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ZooEntity getZoosByZooId() {
        return zoosByZooId;
    }

    public void setZoosByZooId(ZooEntity zoosByZooId) {
        this.zoosByZooId = zoosByZooId;
    }

    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public AnimalEntity getAnimalsByAnimalId() {
        return animalsByAnimalId;
    }

    public void setAnimalsByAnimalId(AnimalEntity animalsByAnimalId) {
        this.animalsByAnimalId = animalsByAnimalId;
    }
}
