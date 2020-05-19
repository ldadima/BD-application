package org.fit.linevich.converters_for_db;

import org.fit.linevich.model.Season;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SeasonConverter implements AttributeConverter<Season, String>  {

    @Override
    public String convertToDatabaseColumn(Season value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    @Override
    public Season convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Season.findByName(value);
    }
}