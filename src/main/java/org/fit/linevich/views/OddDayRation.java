package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.Season;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OddDayRation {
    @NotNull
    private int animalId;
    @NotNull
    private Season season;
    @NotNull
    @Min(0)
    private int quantity;

    public String getSeason() {
        return season.getName();
    }
}
