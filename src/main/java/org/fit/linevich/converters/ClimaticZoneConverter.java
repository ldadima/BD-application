package org.fit.linevich.converters;

import org.fit.linevich.model.ClimaticZone;
import org.springframework.core.convert.converter.Converter;


public class ClimaticZoneConverter implements Converter<String, ClimaticZone> {

    @Override
    public ClimaticZone convert(String source) {
        return ClimaticZone.findByName(source);
    }
}