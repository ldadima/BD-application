package org.fit.linevich.converters;

import org.fit.linevich.model.Development;
import org.springframework.core.convert.converter.Converter;

public class DevelopmentConverter implements Converter<String, Development> {

    @Override
    public Development convert(String source) {
        return Development.findByName(source);
    }
}