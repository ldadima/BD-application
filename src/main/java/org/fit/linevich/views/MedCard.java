package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.Development;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class MedCard {
    @NotNull
    private int animalId;
    @NotNull
    @Min(0)
    private int height;
    @NotNull
    @Min(0)
    private int weight;
    @NotNull
    private Development development;
    private boolean needHospital;
    @NotNull
    private Date dateLastInspection;

    public String getDevelopment() {
        return development.getDevelop();
    }
}
