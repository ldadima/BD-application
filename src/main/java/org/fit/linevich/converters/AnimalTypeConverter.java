package org.fit.linevich.converters;

import org.fit.linevich.model.AnimalType;
import org.springframework.core.convert.converter.Converter;

public class AnimalTypeConverter implements Converter<String, AnimalType> {

    @Override
    public AnimalType convert(String source) {
        return AnimalType.findByName(source);
    }
}