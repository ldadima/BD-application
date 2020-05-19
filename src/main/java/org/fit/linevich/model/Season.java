package org.fit.linevich.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Season {
    WINTER("Зима"),
    SPRING("Весна"),
    SUMMER("Лето"),
    AUTUMN("Осень");
    private final String name;

    Season(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static Season findByName(String name){
        for(Season one: values()){
            if(one.name.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error season for %s", name));
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
