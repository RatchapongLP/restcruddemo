package com.ryoma.restcruddemo.controller;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable int id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new RuntimeException("id not found - " + id);
        }
        return employee;
    }

    @GetMapping("/employees")
    public Employee findIdByInfo(@RequestBody Employee employee) {
        int id = employeeService.findIdByInfo(employee);
        // TODO:
        if (employee == null) {
            throw new RuntimeException("id not found - " + id);
        }
        return employee;
    }

    @GetMapping("/employees")
    public List<Employee> findByFirstName(@RequestParam String firstName) {
        List<Employee> list = employeeService.findByFirstName(firstName);
        // TODO:
        return null;
    }

    @GetMapping("/employees")
    public List<Employee> findByLastName(@RequestParam String lastName) {
        List<Employee> list = employeeService.findByLastName(lastName);
        // TODO:
        return null;
    }

    @GetMapping("/employees")
    public List<Employee> findByEmail(@RequestParam String email) {
        List<Employee> list = employeeService.findByEmail(email);
        // TODO:
        return null;
    }

    @PostMapping("/employees")
    public int createEmployee(@RequestBody Employee employee) {
//        employee.setId(0);
// id of the deserialized employee is always 0 if the JSON request body does not have "id" property.

        return employeeService.addEmployee(employee);
    }

    @PutMapping("/employees")
    public String updateEmployee(@RequestBody Employee employee) {
        try {
            employeeService.updateEmployee(employee);
            return "Update was successful";
        } catch (Exception e) {
            String failMessage = "Update failed with error: " + e.getMessage();
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

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteById(id);
            return "Deletion was successful";
        } catch (Exception e) {
            String failMessage = "Deletion failed with error: " + e.getMessage();
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
