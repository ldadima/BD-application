package org.fit.linevich.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fit.linevich.services.EmployeeService;
import org.fit.linevich.views.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Api
@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/showAll")
    @ApiOperation("Show all employees")
    @ResponseBody
    public List<Employee> showEmployees() {
        return employeeService.showAll();
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

    @DeleteMapping("/deleteEmployeeById")
    @ApiOperation("Delete employee")
    public ResponseEntity<String> deleteEmployeeById(int id){
        employeeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Employee deleted");
    }
}
