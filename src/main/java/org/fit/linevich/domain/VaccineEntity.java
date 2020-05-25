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
@Table(name = "vaccines")
public class VaccineEntity {
    @EmbeddedId
    private VaccineEntityPK vaccineEntityPK;

    @Basic
    @Column(name = "medicine_name", nullable = false, length = -1)
    private String medicineName;
    @Basic
    @Column(name = "dose", nullable = false)
    private Integer dose;
    @Basic
    @Column(name = "date_vaccine", nullable = false)
    private Date dateVaccine;
    @MapsId("animalId")
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
}
