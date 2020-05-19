package org.fit.linevich.converters;

import org.fit.linevich.model.EmployeeCategory;
import org.springframework.core.convert.converter.Converter;

public class EmployeeCategoryConverter implements Converter<String, EmployeeCategory> {

    @Override
    public EmployeeCategory convert(String source) {
        return EmployeeCategory.findByName(source);
    }
}