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
@Table(name = "vaccines", schema = "public", catalog = "bd_zoo")
@IdClass(VaccineEntityPK.class)
public class VaccineEntity {
    @Id
    @Column(name = "vaccine_id", nullable = false)
    private int vaccineId;
    @Basic
    @Column(name = "medicine_name", nullable = false, length = -1)
    private String medicineName;
    @Basic
    @Column(name = "dose", nullable = false)
    private int dose;
    @Basic
    @Column(name = "date_vaccine", nullable = false)
    private Date dateVaccine;
    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
}
