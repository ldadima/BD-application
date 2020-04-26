package org.fit.linevich.model;

public enum  PhysState {
    SICK("Болен"),
    HEALTHY("Здоров");

    private String state;

    PhysState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static PhysState findByName(String name){
        for(PhysState one: values()){
            if(one.state.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error phys state for %s", name));
    }
}
