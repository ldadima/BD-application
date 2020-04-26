package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Data
public class ResponsibleAnimals {
    @NotNull
    private int employerId;
    @NotNull
    private int animalId;
    @NotNull
    private Date dateBegin;
    private Date dateEnd;
}
