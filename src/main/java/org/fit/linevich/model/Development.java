package org.fit.linevich.model;

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

    public static Development findByName(String name){
        for(Development one: values()){
            if(one.develop.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error development for %s", name));
    }

    @Override
    public String toString() {
        return develop;
    }
}
