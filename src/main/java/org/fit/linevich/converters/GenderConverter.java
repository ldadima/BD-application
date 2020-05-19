package org.fit.linevich.converters;

import org.fit.linevich.model.Gender;
import org.springframework.core.convert.converter.Converter;

public class GenderConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String source) {
        return Gender.findByName(source);
    }
}