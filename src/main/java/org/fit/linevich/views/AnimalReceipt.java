package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Objects;

@Data
public class AnimalReceipt {
    @NotNull
    private int zooId;
    @NotNull
    private int animalId;
    @NotNull
    private Date dateReceipt;
}
