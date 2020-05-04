package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Feed {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    @Min(0)
    private Integer stock;
    @NotNull
    @Min(0)
    private Integer volumeIndependentProduction;
}
