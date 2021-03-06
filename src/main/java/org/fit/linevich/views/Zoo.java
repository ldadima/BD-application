package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Zoo {
    private Integer id;
    @NotNull
    private String name;
}
