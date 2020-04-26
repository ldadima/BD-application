package org.fit.linevich.domain;

import org.fit.linevich.model.Season;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EvenDayRationEntityPK implements Serializable {
    private int animalId;
    private Season season;

    @Column(name = "animal_id", nullable = false)
    @Id
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    @Column(name = "season", nullable = false)
    @Id
    public String getSeason() {
        return season.getName();
    }

    public void setSeason(String season) {
        this.season = Season.findByName(season);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvenDayRationEntityPK that = (EvenDayRationEntityPK) o;
        return animalId == that.animalId &&
                Objects.equals(season, that.season);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, season);
    }
}
