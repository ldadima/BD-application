package org.fit.linevich.services;

import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.views.Employee;
import org.fit.linevich.repositories.EmployeesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeesRepo employeesRepo;
    @Autowired
    private CustomDataMapper customDataMapper;

    public List<Employee> showAll(){
        Iterable<EmployeeEntity> employees = employeesRepo.findAll();
        List<Employee> employeeDTOS = new ArrayList<>();
        for (EmployeeEntity employeeEntity :employees){
            employeeDTOS.add(customDataMapper.toEmployeeView(employeeEntity));
        }
        return employeeDTOS;
    }
    public Employee findById(int id){
        Optional<EmployeeEntity> employeeEntity = employeesRepo.findById(id);
        return employeeEntity.map(entity -> customDataMapper.toEmployeeView(entity)).orElse(null);
    }

    public void deleteById(int id){
        employeesRepo.deleteById(id);
    }

    public void create(Employee employee){
        employeesRepo.save(customDataMapper.toEmployeeEntity(employee));
    }
}
