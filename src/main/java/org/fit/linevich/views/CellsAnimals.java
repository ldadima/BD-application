package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class CellsAnimals {
    private Integer cellId;
    @NotNull
    private int animalId;
    @NotNull
    private Date dateBegin;
    private Date dateEnd;
    private boolean heating;
}
