package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductiveAge {
    @NotNull
    private String kind;
    @NotNull
    @Min(0)
    private int age;
}
