package org.fit.linevich.converters;

import org.fit.linevich.model.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String>  {

    @Override
    public String convertToDatabaseColumn(Gender value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    @Override
    public Gender convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Gender.findByName(value);
    }
}