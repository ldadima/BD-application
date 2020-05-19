package org.fit.linevich.config;

import org.fit.linevich.converters.AnimalTypeConverter;
import org.fit.linevich.converters.ClimaticZoneConverter;
import org.fit.linevich.converters.DevelopmentConverter;
import org.fit.linevich.converters.EmployeeCategoryConverter;
import org.fit.linevich.converters.GenderConverter;
import org.fit.linevich.converters.PhysStateConverter;
import org.fit.linevich.converters.SeasonConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AnimalTypeConverter());
        registry.addConverter(new ClimaticZoneConverter());
        registry.addConverter(new DevelopmentConverter());
        registry.addConverter(new EmployeeCategoryConverter());
        registry.addConverter(new GenderConverter());
        registry.addConverter(new PhysStateConverter());
        registry.addConverter(new SeasonConverter());
    }
}
