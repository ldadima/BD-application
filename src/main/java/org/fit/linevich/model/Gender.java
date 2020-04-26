package org.fit.linevich.model;

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public static Gender findByName(String name){
        for(Gender one: values()){
            if(one.gender.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error gender for %s", name));
    }
}
