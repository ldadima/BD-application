package org.fit.linevich.model;

public enum AnimalType {
    PREDATOR("Хищник"),
    HERBIVORE("Травоядное");
    private final String type;

    AnimalType(String type) {
        this.type = type;
    }

    public String getName() {
        return type;
    }

    public static AnimalType findByName(String name){
        for(AnimalType one: values()){
            if(one.type.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error animal type for %s", name));
    }

    @Override
    public String toString() {
        return type;
    }
}
