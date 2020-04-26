package org.fit.linevich.domain;

import org.fit.linevich.model.Season;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "odd_day_ration", schema = "public", catalog = "bd_zoo")
@IdClass(OddDayRationEntityPK.class)
public class OddDayRationEntity {
    private int animalId;
    private Season season;
    private int feedId;
    private int quantity;
    private AnimalEntity animalsByAnimalId;
    private FeedEntity feedsByFeedId;

    @Id
    @Column(name = "animal_id", nullable = false)
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Id
    @Column(name = "season", nullable = false)
    public String getSeason() {
        return season.getName();
    }

    public void setSeason(String season) {
        this.season = Season.findByName(season);
    }

    @Basic
    @Column(name = "feed_id", nullable = false)
    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OddDayRationEntity that = (OddDayRationEntity) o;
        return animalId == that.animalId &&
                feedId == that.feedId &&
                quantity == that.quantity &&
                Objects.equals(season, that.season);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, season, feedId, quantity);
    }

    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public AnimalEntity getAnimalsByAnimalId() {
        return animalsByAnimalId;
    }

    public void setAnimalsByAnimalId(AnimalEntity animalsByAnimalId) {
        this.animalsByAnimalId = animalsByAnimalId;
    }

    @ManyToOne
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public FeedEntity getFeedsByFeedId() {
        return feedsByFeedId;
    }

    public void setFeedsByFeedId(FeedEntity feedsByFeedId) {
        this.feedsByFeedId = feedsByFeedId;
    }
}
