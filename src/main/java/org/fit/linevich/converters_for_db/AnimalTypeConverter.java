package org.fit.linevich.converters_for_db;

import org.fit.linevich.model.AnimalType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AnimalTypeConverter implements AttributeConverter<AnimalType, String>  {

    @Override
    public String convertToDatabaseColumn(AnimalType value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    @Override
    public AnimalType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return AnimalType.findByName(value);
    }
}