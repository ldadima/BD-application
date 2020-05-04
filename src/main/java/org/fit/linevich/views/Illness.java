package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Illness {
    private Integer id;
    @NotNull
    private String name;
}
