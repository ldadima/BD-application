package org.fit.linevich.views;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Feed {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    @Min(0)
    private int stock;
    @NotNull
    @Min(0)
    private int volumeIndependentProduction;
}
