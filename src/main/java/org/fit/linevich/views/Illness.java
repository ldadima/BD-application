package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public class Illness {
    private Integer id;
    @NotNull
    private String name;
}
