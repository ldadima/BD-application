package org.fit.linevich.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Development {
    DEVELOPED("Развит"),
    UNDERDEVELOPED("Недоразвит");

    private final String develop;

    Development(String develop) {
        this.develop = develop;
    }

    public String getName() {
        return develop;
    }

    @JsonCreator
    public static Development findByName(String name){
        for(Development one: values()){
            if(one.develop.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error development for %s", name));
    }

    @Override
    @JsonValue
    public String toString() {
        return develop;
    }
}
