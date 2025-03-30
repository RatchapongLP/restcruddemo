package com.ryoma.restcruddemo.controller;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable int id) {
        return employeeService.findById(id);
    }

    @GetMapping("/employees")
    public List<Employee> findEmployees(@RequestBody(required = false) Employee employee) {
        return employeeService.findEmployees(employee);
    }

    @PostMapping("/employees")
    public String createEmployee(@RequestBody Employee employee) {
//        employee.setId(0); // id is an int instance variable, so its default is 0
        try {
            return String.valueOf(employeeService.addEmployee(employee));
        } catch (Exception e) {
            String failMessage = "Failed with error: " + e.getMessage();
            if (e.getCause() != null) {
                failMessage += "\ncause: " + e.getCause();
            }
            if (e.getStackTrace() != null && e.getStackTrace().length != 0) {
                failMessage += "\nstack trace: ";
                for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                    failMessage += "\n" + stackTraceElement.toString();
                }
            }
            return failMessage;
        }
    }

    @PutMapping("/employees")
    public String updateEmployee(@RequestBody Employee employee) {
        try {
            employeeService.updateEmployee(employee);
            return "Success";
        } catch (Exception e) {
            String failMessage = "Failed with error: " + e.getMessage();
            if (e.getCause() != null) {
                failMessage += "\ncause: " + e.getCause();
            }
            if (e.getStackTrace() != null && e.getStackTrace().length != 0) {
                failMessage += "\nstack trace: ";
                for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                    failMessage += "\n" + stackTraceElement.toString();
                }
            }
            return failMessage;
        }
    }

    @DeleteMapping("/employees")
    public String deleteEmployee(@RequestParam int id) {
        try {
            employeeService.deleteById(id);
            return "Success";
        } catch (Exception e) {
            String failMessage = "Failed with error: " + e.getMessage();
            if (e.getCause() != null) {
                failMessage += "\ncause: " + e.getCause();
            }
            if (e.getStackTrace() != null && e.getStackTrace().length != 0) {
                failMessage += "\nstack trace: ";
                for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                    failMessage += "\n" + stackTraceElement.toString();
                }
            }
            return failMessage;
        }
    }
}
