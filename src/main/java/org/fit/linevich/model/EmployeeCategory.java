package org.fit.linevich.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EmployeeCategory {
    VET("Ветеринар"),
    CLEANER("Уборщик"),
    ADMIN("Работник администрации"),
    BUILDER("Строитель-ремонтник"),
    TRAINER("Дрессировщик");

    private final String category;

    EmployeeCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return category;
    }

    @JsonCreator
    public static EmployeeCategory findByName(String name){
        for(EmployeeCategory one: values()){
            if(one.category.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error employee category %s", name));
    }

    @Override
    @JsonValue
    public String toString() {
        return category;
    }
}
