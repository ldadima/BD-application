package org.fit.linevich.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum  PhysState {
    SICK("Болен"),
    HEALTHY("Здоров");

    private final String state;

    PhysState(String state) {
        this.state = state;
    }

    public String getName() {
        return state;
    }

    @JsonCreator
    public static PhysState findByName(String name){
        for(PhysState one: values()){
            if(one.state.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error phys state for %s", name));
    }

    @Override
    @JsonValue
    public String toString() {
        return state;
    }
}
