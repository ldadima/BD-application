package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class Provider {
    @NotNull
    private Integer provId;
    @NotNull
    private String name;
    @NotNull
    private Date dateBegin;
    private Date dateEnd;
}
