package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class Vaccine {
    @NotNull
    private int animalId;
    @NotNull
    private int vaccineId;
    @NotNull
    private String medicineName;
    @NotNull
    @Min(0)
    private int dose;
    @NotNull
    private Date dateVaccine;
}
