package org.fit.linevich.converters;

import org.fit.linevich.model.Season;
import org.springframework.core.convert.converter.Converter;

public class SeasonConverter implements Converter<String, Season> {

    @Override
    public Season convert(String source) {
        return Season.findByName(source);
    }
}