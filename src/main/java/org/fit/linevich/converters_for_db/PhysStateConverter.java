package org.fit.linevich.converters_for_db;

import org.fit.linevich.model.PhysState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PhysStateConverter implements AttributeConverter<PhysState, String>  {

    @Override
    public String convertToDatabaseColumn(PhysState value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    @Override
    public PhysState convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return PhysState.findByName(value);
    }
}