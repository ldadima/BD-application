package org.fit.linevich.views;

import lombok.Data;
import org.fit.linevich.model.Development;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class AnimalMed {
    private Animal animal;
    @Min(value = 0, message = "Рост больше 0")
    private Integer height;
    @Min(value = 0, message = "Вес больше 0")
    private Integer weight;
    private Development development;
    private Boolean needHospital;
    private LocalDate dateLast;
}
