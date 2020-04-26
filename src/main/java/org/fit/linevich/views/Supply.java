package org.fit.linevich.views;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Data
public class Supply {
    private Integer id;
    @NotNull
    private BigDecimal price;
    @NotNull
    @Min(0)
    private int feedAmount;
    @NotNull
    private Date dateSupply;
}
