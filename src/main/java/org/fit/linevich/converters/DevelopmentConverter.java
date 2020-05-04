package org.fit.linevich.converters;

import org.fit.linevich.model.Development;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DevelopmentConverter implements AttributeConverter<Development, String>  {

    @Override
    public String convertToDatabaseColumn(Development value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    @Override
    public Development convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Development.findByName(value);
    }
}