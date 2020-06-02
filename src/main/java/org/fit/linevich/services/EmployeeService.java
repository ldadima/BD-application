package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.AccessAnimalsEntity;
import org.fit.linevich.domain.AccessAnimalsEntityPK;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.domain.ResponsibleAnimalsEntity;
import org.fit.linevich.domain.ResponsibleAnimalsEntityPK;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.repositories.AnimalsRepo;
import org.fit.linevich.repositories.EmployeesRepo;
import org.fit.linevich.views.Employee;
import org.fit.linevich.views.ResponsibleAnimalQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeesRepo employeesRepo;
    private final AnimalsRepo animalsRepo;
    private final CustomDataMapper customDataMapper;

    public Page<Employee> showAll(int page, int size) {
        Page<EmployeeEntity> employees = employeesRepo.findAll(PageRequest.of(page, size,
                Sort.by("id").ascending()));
        return customDataMapper.toEmployeePage(employees);
    }

    public Employee findById(int id) {
        Optional<EmployeeEntity> employeeEntity = employeesRepo.findById(id);
        return employeeEntity.map(customDataMapper::toEmployeeView).orElse(null);
    }

    public void deleteById(int id) {
        employeesRepo.deleteById(id);
    }

    public void create(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        customDataMapper.toEmployeeEntity(employee, employeeEntity);
        employeesRepo.save(employeeEntity);
    }

    public boolean update(Employee employee) {
        EmployeeEntity employeeEntity = employeesRepo.findById(employee.getId())
                .orElse(null);
        if (employeeEntity == null) {
            return false;
        }
        customDataMapper.toEmployeeEntity(employee, employeeEntity);
        employeesRepo.save(employeeEntity);
        return true;
    }

    public void addAccess(int employeeId, int animalId) {
        EmployeeEntity employeeEntity = employeesRepo.findById(employeeId).orElse(null);
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (employeeEntity == null || animalEntity == null) {
            return;
        }
        employeeEntity.getAccessAnimalsById()
                .add(new AccessAnimalsEntity(new AccessAnimalsEntityPK(employeeId, animalId), employeeEntity,
                        animalEntity));
        employeesRepo.save(employeeEntity);
    }

    public void deleteAccess(int employeeId, int animalId) {
        EmployeeEntity employeeEntity = employeesRepo.findById(employeeId).orElse(null);
        if (employeeEntity == null) {
            return;
        }
        employeeEntity.getAccessAnimalsById()
                .removeIf(accessAnimalsEntity -> accessAnimalsEntity.getAccessAnimalsEntityPK().getAnimalId() ==
                        animalId);
        employeesRepo.save(employeeEntity);
    }
    
    public void addResponsible(int employeeId, int animalId, LocalDate dateBegin, LocalDate dateEnd) {
        EmployeeEntity employeeEntity = employeesRepo.findById(employeeId).orElse(null);
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (employeeEntity == null || animalEntity == null) {
            return;
        }
        employeeEntity.getResponsibleAnimalsById()
                .add(new ResponsibleAnimalsEntity(new ResponsibleAnimalsEntityPK(employeeId, animalId), Date.valueOf(dateBegin),
                        Date.valueOf(dateEnd), employeeEntity, animalEntity));
        employeesRepo.save(employeeEntity);
    }

    public void deleteResponsible(int employeeId, int animalId) {
        EmployeeEntity employeeEntity = employeesRepo.findById(employeeId).orElse(null);
        if (employeeEntity == null) {
            return;
        }
        employeeEntity.getResponsibleAnimalsById()
                .removeIf(accessAnimalsEntity -> accessAnimalsEntity.getResponsibleAnimalsEntityPK().getAnimalId() ==
                        animalId);
        employeesRepo.save(employeeEntity);
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
    public Page<Employee> findByCategory(int page, int size, EmployeeCategory category) {
        Page<EmployeeEntity> employees =
                employeesRepo.getEmployeeEntitiesByCategory(PageRequest.of(page, size), category);
        return customDataMapper.toEmployeePage(employees);
    }

    /**
     * 1-ый запрос
     */
    public Page<Employee> orderByDuration(int page, int size) {
        Page<EmployeeEntity> employees =
                employeesRepo.orderByDuration(PageRequest.of(page, size));
        return customDataMapper.toEmployeePage(employees);
    }

    /**
     * 1-ый запрос
     */
    public Page<Employee> orderByAge(int page, int size) {
        Page<EmployeeEntity> employees =
                employeesRepo.orderByAge(PageRequest.of(page, size));
        return customDataMapper.toEmployeePage(employees);
    }

    /**
     * 1-ый запрос
     */
    public Page<Employee> orderByGender(int page, int size) {
        Page<EmployeeEntity> employees =
                employeesRepo.orderByGender(PageRequest.of(page, size));
        return customDataMapper.toEmployeePage(employees);
    }

    /**
     * 1-ый запрос
     */
    public Page<Employee> orderBySalary(int page, int size) {
        Page<EmployeeEntity> employees =
                employeesRepo.orderBySalary(PageRequest.of(page, size));
        return customDataMapper.toEmployeePage(employees);
    }

    /**
     * 2-ой запрос
     */
    public Page<Employee> responsibleAnimal(ResponsibleAnimalQuery query) {
        Page<EmployeeEntity> employees =
                employeesRepo.responsibleAnimal(PageRequest.of(query.getPage(), query.getSize()), query.getKind(), query.getBegin(), query.getEnd(), Date.valueOf(LocalDate.now()));
        return customDataMapper.toEmployeePage(employees);
    }

    /**
     * 3-ий запрос
     */
    public Page<Employee> accessAnimal(int page, int size, String kind) {
        Page<EmployeeEntity> employees =
                employeesRepo.accessAnimal(PageRequest.of(page, size), kind);
        return customDataMapper.toEmployeePage(employees);
    }
}
