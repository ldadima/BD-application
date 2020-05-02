package org.fit.linevich.model;

public enum EmployeeCategory {
    VET("Ветеринар"),
    CLEANER("Уборщик"),
    ADMIN("Работник администрации"),
    BUILDER("Строитель-ремонтник"),
    TRAINER("Дрессировщик");

    private final String category;

    EmployeeCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public static EmployeeCategory findByName(String name){
        for(EmployeeCategory one: values()){
            if(one.category.equals(name))
                return one;
        }
        throw new IllegalArgumentException(String.format("Error employee category %s", name));
    }
}
