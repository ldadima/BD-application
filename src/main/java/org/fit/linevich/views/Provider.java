package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Provider {
    @NotNull
    private Integer provId;
    @NotNull
    private String name;
    @NotNull
    private LocalDate dateBegin;
    private LocalDate dateEnd;
}
