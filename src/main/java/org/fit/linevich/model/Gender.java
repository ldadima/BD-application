package org.fit.linevich.model;

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return gender;
    }

    public static Gender findByName(String name){
        for(Gender one: values()){
            if(one.gender.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error gender for %s", name));
    }

    @Override
    public String toString() {
        return gender;
    }
}
