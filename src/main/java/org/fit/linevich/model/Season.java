package org.fit.linevich.model;

public enum Season {
    WINTER("Зима"),
    SPRING("Весна"),
    SUMMER("Лето"),
    AUTUMN("Осень");
    private String name;

    Season(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Season findByName(String name){
        for(Season one: values()){
            if(one.name.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error season for %s", name));
    }
}
