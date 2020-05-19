package org.fit.linevich.converters_for_db;

import org.fit.linevich.model.EmployeeCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EmployeeCategoryConverter implements AttributeConverter<EmployeeCategory, String>  {

    @Override
    public String convertToDatabaseColumn(EmployeeCategory value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    @Override
    public EmployeeCategory convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return EmployeeCategory.findByName(value);
    }
}