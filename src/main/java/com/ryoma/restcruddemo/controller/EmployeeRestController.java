package com.ryoma.restcruddemo.controller;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private final EmployeeService employeeService;
    private Logger logger;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        init();
    }

    private void init() {
        logger = LogManager.getLogger(EmployeeRestController.class.getSimpleName());
        if (logger == null) {
            System.out.println("Something went wrong when creating logger for " + EmployeeRestController.class.getName());
        }
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable int id) {
        logger.info("GET: requested for an employee with id - " + id);
        return employeeService.findById(id);
    }

    @GetMapping("/employees")
    public List<Employee> findEmployees(@RequestBody(required = false) Employee employee) {
        logger.info("GET: requested for a list of employee with matching properties - " + employee);
        return employeeService.findEmployees(employee);
    }

    @PostMapping("/employees")
    public String createEmployee(@RequestBody Employee employee) {
        logger.info("POST: requested to create an employee - " + employee);
//        employee.setId(0); // id is an int instance variable, so its default is 0
        try {
            int idGenerated = employeeService.addEmployee(employee);
            logger.info("Successfully created an employee of id - " + idGenerated);
            return String.valueOf(idGenerated);
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
            logger.error(failMessage);
            return failMessage;
        }
    }

    @PutMapping("/employees")
    public String updateEmployee(@RequestBody Employee employee) {
        logger.info("PUT: requested to update an employee - " + employee);
        try {
            employeeService.updateEmployee(employee);
            logger.info("Successfully updated");
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
            logger.error(failMessage);
            return failMessage;
        }
    }

    @DeleteMapping("/employees")
    public String deleteEmployee(@RequestParam int id) {
        logger.info("DELETE: requested to delete an employee with id - " + id);
        try {
            employeeService.deleteById(id);
            logger.info("Successfully deleted an employee");
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
            logger.error(failMessage);
            return failMessage;
        }
    }
}
