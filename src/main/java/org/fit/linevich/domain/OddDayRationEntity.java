package org.fit.linevich.domain;

import lombok.Data;
import org.fit.linevich.converters_for_db.SeasonConverter;
import org.fit.linevich.model.Season;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "odd_day_ration", schema = "public", catalog = "bd_zoo")
@IdClass(OddDayRationEntityPK.class)
public class OddDayRationEntity {
    @Id
    @Column(name = "season", nullable = false)
    @Convert(converter = SeasonConverter.class)
    private Season season;
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private FeedEntity feedId;
}
