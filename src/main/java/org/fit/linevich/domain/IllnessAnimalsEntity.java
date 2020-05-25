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
@Table(name = "illness_animals")
public class IllnessAnimalsEntity {
    @EmbeddedId
    private IllnessAnimalsEntityPK illnessAnimalsEntityPK;

    @Basic
    @Column(name = "date_begin", nullable = true)
    private Date dateBegin;
    @Basic
    @Column(name = "date_end", nullable = true)
    private Date dateEnd;
    @MapsId("animalId")
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
    @MapsId("illnessId")
    @ManyToOne
    @JoinColumn(name = "illness_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private IllnessEntity illnessId;
}
