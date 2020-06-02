package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fit.linevich.domain.AccessAnimalsEntity;
import org.fit.linevich.domain.AccessAnimalsEntityPK;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.domain.ResponsibleAnimalsEntity;
import org.fit.linevich.domain.ResponsibleAnimalsEntityPK;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.services.EmployeeService;
import org.fit.linevich.views.Employee;
import org.fit.linevich.views.ResponsibleAnimalQuery;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/showAll")
    @ApiOperation("Show all employees")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployees(int page, int size) {
        return ResponseEntity.ok(employeeService.showAll(page, size));
    }

    @GetMapping("/byCategory")
    @ApiOperation("Show employees by category")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployeesByCategory(int page, int size, EmployeeCategory category) {
        Page<Employee> employees = employeeService.findByCategory(page, size, category);
        if (employees.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/orderByDuration")
    @ApiOperation("Show employees order by duration work")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployeesOrderByDuration(int page, int size) {
        Page<Employee> employees = employeeService.orderByDuration(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/orderByAge")
    @ApiOperation("Show employees order by age")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployeesOrderByAge(int page, int size) {
        Page<Employee> employees = employeeService.orderByAge(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/orderByGender")
    @ApiOperation("Show employees order by gender")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployeesOrderByGender(int page, int size) {
        Page<Employee> employees = employeeService.orderByGender(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/orderBySalary")
    @ApiOperation("Show employees order by salary")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployeesOrderBySalary(int page, int size) {
        Page<Employee> employees = employeeService.orderBySalary(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @PutMapping("/responsibleAnimalQuery")
    @ApiOperation("Show employees responsible for the kind of animal in a given period")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployeesResponsibleAnimalQuery(@RequestBody ResponsibleAnimalQuery query) {
        Page<Employee> employees = employeeService.responsibleAnimal(query);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/accessAnimalQuery")
    @ApiOperation("Show employees access for the kind of animal")
    @ResponseBody
    public ResponseEntity<Page<Employee>> showEmployeesAccessAnimalQuery(int page, int size, String kindAnimal) {
        Page<Employee> employees = employeeService.accessAnimal(page, size, kindAnimal);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/employeeById")
    @ApiOperation("Show employee by id")
    @ResponseBody
    public ResponseEntity<Employee> employeeById(int id) {
        Employee answer = employeeService.findById(id);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("/createEmployee")
    @ApiOperation("Create employee")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody Employee employee){
        employeeService.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee added");
    }

    @PutMapping("/updateEmployee")
    @ApiOperation("Update employee")
    public ResponseEntity<String> updateEmployee(@Valid @RequestBody Employee employee){
        if(employeeService.update(employee)) {
            return ResponseEntity.status(HttpStatus.OK).body("Employee updated");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Employee not found");
        }
    }

    @DeleteMapping("/deleteEmployeeById")
    @ApiOperation("Delete employee")
    public ResponseEntity<String> deleteEmployeeById(int id){
        employeeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Employee deleted");
    }

    @GetMapping("/count")
    @ApiOperation("Count of employees")
    public  ResponseEntity<Long> countEmployees(){
        Long count = employeeService.count();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @PostMapping("/addAccess")
    public ResponseEntity<String> addAccess(int employeeId, int animalId) {
        employeeService.addAccess(employeeId, animalId);
        return ResponseEntity.status(HttpStatus.OK).body("Access to animal added");
    }

    @DeleteMapping("/deleteAccess")
    public ResponseEntity<String> deleteAccess(int employeeId, int animalId) {
        employeeService.deleteAccess(employeeId, animalId);
        return ResponseEntity.status(HttpStatus.OK).body("Access to animal deleted");
    }

    @PostMapping("/addResponsible")
    public ResponseEntity<String> addResponsible(int employeeId, int animalId, LocalDate dateBegin, LocalDate dateEnd) {
        employeeService.addResponsible(employeeId, animalId, dateBegin, dateEnd);
        return ResponseEntity.status(HttpStatus.OK).body("Responsible to animal added");
    }

    @DeleteMapping("/deleteResponsible")
    public ResponseEntity<String> deleteResponsible(int employeeId, int animalId) {
        employeeService.deleteResponsible(employeeId, animalId);
        return ResponseEntity.status(HttpStatus.OK).body("Responsible to animal deleted");
    }

}
