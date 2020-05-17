package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.fit.linevich.converters.EmployeeCategoryConverter;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.services.EmployeeService;
import org.fit.linevich.views.Employee;
import org.fit.linevich.views.ResponsibleAnimalQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.validation.Valid;
import java.util.List;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/showAll")
    @ApiOperation("Show all employees")
    @ResponseBody
    public List<Employee> showEmployees() {
        return employeeService.showAll();
    }

    @GetMapping("/byCategory")
    @ApiOperation("Show employees by category")
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployeesByCategory(String category) {
        List<Employee> employees = employeeService.findByCategory(category);
        if (employees.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/groupByDuration")
    @ApiOperation("Show employees group by duration work")
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployeesGroupByDuration() {
        List<Employee> employees = employeeService.groupByDuration();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/groupByAge")
    @ApiOperation("Show employees group by age")
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployeesGroupByAge() {
        List<Employee> employees = employeeService.groupByAge();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/groupByGender")
    @ApiOperation("Show employees group by gender")
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployeesGroupByGender() {
        List<Employee> employees = employeeService.groupByGender();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/groupBySalary")
    @ApiOperation("Show employees group by salary")
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployeesGroupBySalary() {
        List<Employee> employees = employeeService.groupBySalary();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @PutMapping("/responsibleAnimalQuery")
    @ApiOperation("Show employees responsible for the kind of animal in a given period")
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployeesResponsibleAnimalQuery(@RequestBody ResponsibleAnimalQuery query) {
        List<Employee> employees = employeeService.responsibleAnimal(query);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @PutMapping("/accessAnimalQuery")
    @ApiOperation("Show employees access for the kind of animal")
    @ResponseBody
    public ResponseEntity<List<Employee>> showEmployeesResponsibleAnimalQuery(String kindAnimal) {
        List<Employee> employees = employeeService.responsibleAnimal(kindAnimal);
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
}
