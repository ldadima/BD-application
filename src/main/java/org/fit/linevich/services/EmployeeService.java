package org.fit.linevich.services;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.repositories.EmployeesRepo;
import org.fit.linevich.views.Employee;
import org.fit.linevich.views.ResponsibleAnimalQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeesRepo employeesRepo;
    private final CustomDataMapper customDataMapper;

    public List<Employee> showAll() {
        List<EmployeeEntity> employees = employeesRepo.findAll();
        return customDataMapper.toEmployeeListView(employees);
    }

    public Employee findById(int id) {
        Optional<EmployeeEntity> employeeEntity = employeesRepo.findById(id);
        return employeeEntity.map(customDataMapper::toEmployeeView).orElse(null);
    }

    public void deleteById(int id) {
        employeesRepo.deleteById(id);
    }

    public void create(Employee employee){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        customDataMapper.toEmployeeEntity(employee, employeeEntity);
        employeesRepo.save(employeeEntity);
    }

    public boolean update(Employee employee){
        EmployeeEntity employeeEntity = employeesRepo.findById(employee.getId())
                .orElse(null);
        if (employeeEntity == null){
            return false;
        }
        customDataMapper.toEmployeeEntity(employee, employeeEntity);
        employeesRepo.save(employeeEntity);
        return true;
    }

    /**
     * 1-ый запрос
     */
    public long count() {
        return employeesRepo.count();
    }


    /**
     * 1-ый запрос
     */
    public List<Employee> findByCategory(String category) {
        List<EmployeeEntity> employees =
                employeesRepo.getEmployeeEntitiesByCategory(EmployeeCategory.findByName(category));
        return customDataMapper.toEmployeeListView(employees);
    }

    /**
     * 1-ый запрос
     */
    public List<Employee> groupByDuration(){
        List<EmployeeEntity> employees =
                employeesRepo.groupByDuration();
        return customDataMapper.toEmployeeListView(employees);
    }

    /**
     * 1-ый запрос
     */
    public List<Employee> groupByAge(){
        List<EmployeeEntity> employees =
                employeesRepo.groupByAge();
        return customDataMapper.toEmployeeListView(employees);
    }

    /**
     * 1-ый запрос
     */
    public List<Employee> groupByGender(){
        List<EmployeeEntity> employees =
                employeesRepo.groupByGender();
        return customDataMapper.toEmployeeListView(employees);
    }

    /**
     * 1-ый запрос
     */
    public List<Employee> groupBySalary(){
        List<EmployeeEntity> employees =
                employeesRepo.groupBySalary();
        return customDataMapper.toEmployeeListView(employees);
    }

    /**
     * 2-ой запрос
     */
    public List<Employee> responsibleAnimal(ResponsibleAnimalQuery query){
        List<EmployeeEntity> employees =
                employeesRepo.responsibleAnimal(query.getKind(), query.getBegin(), query.getEnd());
        return customDataMapper.toEmployeeListView(employees);
    }

    /**
     * 3-ий запрос
     */
    public List<Employee> responsibleAnimal(String kind){
        List<EmployeeEntity> employees =
                employeesRepo.accessAnimal(kind);
        return customDataMapper.toEmployeeListView(employees);
    }
}
