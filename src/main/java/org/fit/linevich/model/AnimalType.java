package org.fit.linevich.model;

public enum AnimalType {
    PREDATOR("Хищник"),
    HERBIVORE("Травоядное");
    private String type;

    AnimalType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static AnimalType findByName(String name){
        for(AnimalType one: values()){
            if(one.type.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error animal type for %s", name));
    }
}
