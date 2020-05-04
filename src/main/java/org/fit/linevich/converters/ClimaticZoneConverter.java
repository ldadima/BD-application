package org.fit.linevich.converters;

import org.fit.linevich.model.ClimaticZone;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ClimaticZoneConverter implements AttributeConverter<ClimaticZone, String>  {

    @Override
    public String convertToDatabaseColumn(ClimaticZone value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    @Override
    public ClimaticZone convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return ClimaticZone.findByName(value);
    }
}