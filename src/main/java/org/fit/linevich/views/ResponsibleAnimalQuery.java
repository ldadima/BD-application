package org.fit.linevich.views;

import lombok.Data;

import java.sql.Date;


@Data
public class ResponsibleAnimalQuery {
    private String kind;
    private Date begin;
    private Date end;
}
