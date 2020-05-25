package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fit.linevich.converters_for_db.DevelopmentConverter;
import org.fit.linevich.model.Development;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "med_card")
public class MedCardEntity {
    @Id
    @Column(name = "animal_id")
    private Integer animalId;
    @Basic
    @Column(name = "height", nullable = false)
    private Integer height;
    @Basic
    @Column(name = "weight", nullable = false)
    private Integer weight;
    @Basic
    @Column(name = "development", nullable = false)
    @Convert(converter = DevelopmentConverter.class)
    private Development development;
    @Basic
    @Column(name = "need_hospital", nullable = false)
    private Boolean needHospital;
    @Basic
    @Column(name = "date_last_inspection", nullable = false)
    private Date dateLastInspection;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "animal_id", referencedColumnName = "id")
    private AnimalEntity animal;
}
