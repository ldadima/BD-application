package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.model.Gender;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class Employee {
    private Integer id;
    @NotNull
    private String surname;
    @NotNull
    private String name;
    @NotNull
    private String patronymic;
    @NotNull
    private EmployeeCategory category;
    @NotNull
    private Integer durationWork;
    @NotNull
    @Size(min = 18, max =100)
    private Integer age;
    @NotNull
    private Gender gender;
    @NotNull
    @Min(0)
    private BigDecimal salary;

    public String getCategory() {
        return category.getName();
    }

    public String getGender() {
        return gender.getName();
    }
}
