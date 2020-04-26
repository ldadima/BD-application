package org.fit.linevich.domain;

import org.fit.linevich.model.Development;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "med_card", schema = "public", catalog = "bd_zoo")
public class MedCardEntity {
    private int animalId;
    private int height;
    private int weight;
    private Development development;
    private boolean needHospital;
    private Date dateLastInspection;
    private AnimalEntity animalsByAnimalId;

    @Id
    @Column(name = "animal_id", nullable = false)
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Basic
    @Column(name = "height", nullable = false)
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Basic
    @Column(name = "weight", nullable = false)
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "development", nullable = false)
    public String getDevelopment() {
        return development.getDevelop();
    }

    public void setDevelopment(String development) {
        this.development = Development.findByName(development);
    }

    @Basic
    @Column(name = "need_hospital", nullable = false)
    public boolean isNeedHospital() {
        return needHospital;
    }

    public void setNeedHospital(boolean needHospital) {
        this.needHospital = needHospital;
    }

    @Basic
    @Column(name = "date_last_inspection", nullable = false)
    public Date getDateLastInspection() {
        return dateLastInspection;
    }

    public void setDateLastInspection(Date dateLastInspection) {
        this.dateLastInspection = dateLastInspection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedCardEntity that = (MedCardEntity) o;
        return animalId == that.animalId &&
                height == that.height &&
                weight == that.weight &&
                needHospital == that.needHospital &&
                Objects.equals(development, that.development) &&
                Objects.equals(dateLastInspection, that.dateLastInspection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, height, weight, development, needHospital, dateLastInspection);
    }

    @OneToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public AnimalEntity getAnimalsByAnimalId() {
        return animalsByAnimalId;
    }

    public void setAnimalsByAnimalId(AnimalEntity animalsByAnimalId) {
        this.animalsByAnimalId = animalsByAnimalId;
    }
}
