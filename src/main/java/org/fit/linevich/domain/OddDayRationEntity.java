package org.fit.linevich.domain;

import lombok.Data;
import org.fit.linevich.model.Season;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table(name = "odd_day_ration", schema = "public", catalog = "bd_zoo")
@IdClass(OddDayRationEntityPK.class)
public class OddDayRationEntity {
    @Id
    @Column(name = "season", nullable = false)
    private String seasonString;
    @Transient
    private Season season;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AnimalEntity animalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private FeedEntity feedId;

    public String getSeason() {
        return season.getName();
    }

    public void setSeason(String season) {
        this.season = Season.findByName(season);
    }

    @PrePersist
    void pre(){
        this.seasonString = season.getName();
    }

    @PostLoad
    void post(){
        setSeason(seasonString);
    }
}
