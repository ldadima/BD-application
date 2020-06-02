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
    @Min(value = 0, message = "Объем(склад) не меньше 0")
    private Integer stock;
    @NotNull
    @Min(value = 0, message = "Объем(производство) не меньше 0")
    private Integer volumeIndependentProduction;
}
