package org.fit.linevich.model;

public enum ClimaticZone {
    EQUATORIAL_FOREST("Экваториальный лес"),
    SUBTROPICAL_FOREST("Субтропический лес"),
    HARD_LEAVED_FOREST("Жестколистный лес"),
    DESERT("Пустыни"),
    STEPPE("Степь"),
    FOREST_STEPPE("Лесостепь"),
    BROAD_LEAF_FOREST("Широколиственный лес"),
    MIXED_FOREST("Смешанный лес"),
    TAIGA("Тайга"),
    FOREST_TUNDRA("Лесотундра"),
    TUNDRA("Тундра"),
    POLAR_DESERT("Полярные пустыни");

    private final String zone;

    ClimaticZone(String zone) {
        this.zone = zone;
    }

    public String getName() {
        return zone;
    }

    public static ClimaticZone findByName(String name){
        for(ClimaticZone one: values()){
            if(one.zone.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error climatic zone for %s", name));
    }

    @Override
    public String toString() {
        return zone;
    }
}