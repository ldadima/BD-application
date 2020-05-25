package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.Gender;

import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;

@Data
public class AnimalCellQuery {
    private String name;
    private String kindAnimal;
    private Gender gender;
    private Integer height;
    private Integer weight;
    private Integer age;

    public AnimalCellQuery(String name, String kindAnimal, Gender gender, Integer height, Integer weight,
            Date birthday) {
        this.name = name;
        this.kindAnimal = kindAnimal;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        Period period = Period.between(LocalDate.now(), LocalDate.parse(birthday.toString()));
        this.age = period.getYears()*12+period.getMonths();
    }
}
