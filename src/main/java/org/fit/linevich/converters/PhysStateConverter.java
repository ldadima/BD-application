package org.fit.linevich.converters;

import org.fit.linevich.model.PhysState;
import org.springframework.core.convert.converter.Converter;

public class PhysStateConverter implements Converter<String, PhysState> {

    @Override
    public PhysState convert(String source) {
        return PhysState.findByName(source);
    }
}