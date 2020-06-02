package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.model.Gender;

import javax.validation.constraints.Max;
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
    @Min(value = 0, message ="Продолжительность не меньше 0")
    private Integer durationWork;
    @NotNull
    @Min(value = 18, message ="Возраст не меньше 18")
    @Max(value = 100, message ="Возраст не больше 100")
    private Integer age;
    @NotNull
    private Gender gender;
    @NotNull
    @Min(0)
    private BigDecimal salary;
}
