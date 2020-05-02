package org.fit.linevich.domain;

import lombok.Data;
import org.fit.linevich.model.Development;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;

@Data
@Entity
@Table(name = "med_card", schema = "public", catalog = "bd_zoo")
public class MedCardEntity {
    @Id
    @Column(name = "animal_id")
    private int animalId;
    @Basic
    @Column(name = "height", nullable = false)
    private int height;
    @Basic
    @Column(name = "weight", nullable = false)
    private int weight;
    @Basic
    @Column(name = "development", nullable = false)
    private String developString;
    @Transient
    private Development development;
    @Basic
    @Column(name = "need_hospital", nullable = false)
    private boolean needHospital;
    @Basic
    @Column(name = "date_last_inspection", nullable = false)
    private Date dateLastInspection;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "animal_id", referencedColumnName = "id")
    private AnimalEntity animal;

    public String getDevelopment() {
        return development.getDevelop();
    }

    public void setDevelopment(String development) {
        this.development = Development.findByName(development);
    }

    @PrePersist
    void pre(){
        developString = development.getDevelop();
    }

    @PostLoad
    void post(){
        setDevelopment(developString);
    }
}
