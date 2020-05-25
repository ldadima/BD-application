package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "odd_day_ration")
public class OddDayRationEntity {
    @EmbeddedId
    private OddDayRationEntityPK oddDayRationEntityPK;

    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @MapsId("animalId")
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private FeedEntity feedId;
}
