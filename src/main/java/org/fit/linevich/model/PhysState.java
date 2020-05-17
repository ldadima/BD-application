package org.fit.linevich.model;

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

    public static PhysState findByName(String name){
        for(PhysState one: values()){
            if(one.state.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error phys state for %s", name));
    }

    @Override
    public String toString() {
        return state;
    }
}
