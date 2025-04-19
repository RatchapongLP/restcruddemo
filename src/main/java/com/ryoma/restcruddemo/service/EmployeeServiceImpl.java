package com.ryoma.restcruddemo.service;

import com.ryoma.restcruddemo.dao.EmployeeDao;
import com.ryoma.restcruddemo.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeDao employeeDao;
    private Logger logger;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
        init();
    }

    private void init() {
//        logger = LogManager.getLogger(EmployeeServiceImpl.class);
        logger = LogManager.getLogger();
        if (logger == null) {
            System.out.println("Something went wrong when creating logger for " + EmployeeServiceImpl.class.getName());
        }
    }

    @Override
    public Employee findById(int id) {
        logger.info("findById: id - " + id);
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> findEmployees(Employee employee) {
        logger.info("findEmployees: employee - " + employee);
        if (employee == null) {
            return employeeDao.findAll();
        }

        if (employee.getFirstName() != null && employee.getLastName() != null) {
            return List.of(employeeDao.findByFullName(employee.getFirstName(), employee.getLastName()));
        }
        if (employee.getFirstName() != null) {
            return employeeDao.findByFirstName(employee.getFirstName());
        }
        if (employee.getLastName() != null) {
            return employeeDao.findByLastName(employee.getLastName());
        }
        if (employee.getEmail() != null) {
            return List.of(employeeDao.findByEmail(employee.getEmail()));
        }

        return employeeDao.findAll();
    }

    @Override
    @Transactional
    public int addEmployee(Employee employee) {
        logger.info("addEmployee: employee - " + employee);
        Employee employeeWithFullName = employeeDao.findByFullName(employee.getFirstName(), employee.getLastName());
        if (employeeWithFullName != null) {
            throw new IllegalArgumentException("Employee with name - " + employeeWithFullName.getFirstName() + " " + employeeWithFullName.getLastName() + " already exists.");
        }
        Employee employeeWithEmail = employeeDao.findByEmail(employee.getEmail());
        if (employeeWithEmail != null) {
            throw new IllegalArgumentException("Employee with email - " + employeeWithEmail.getEmail() + " already exists.");
        }
        return employeeDao.addEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        logger.info("updateEmployee: employee - " + employee);
        Employee employeeWithId = findById(employee.getId());
        if (employeeWithId == null) {
            logger.info("No employee with id - " + employee.getId() + " found.");
            return;
        }

        Employee employeeWithFullName = employeeDao.findByFullName(employee.getFirstName(), employee.getLastName());
        if (employeeWithFullName != null && employeeWithFullName.getId() != employee.getId()) {
            throw new IllegalArgumentException("Employee with name - " + employeeWithFullName.getFirstName() + " " + employeeWithFullName.getLastName() + " already exists.");
        }
        Employee employeeWithEmail = employeeDao.findByEmail(employee.getEmail());
        if (employeeWithEmail != null && employeeWithEmail.getId() != employee.getId()) {
            throw new IllegalArgumentException("Employee with email - " + employeeWithEmail.getEmail() + " already exists.");
        }
        if (employeeWithFullName != null && employeeWithEmail != null) {
            logger.info("Exact matched record found for the update query, early quit updating in " + EmployeeServiceImpl.class.getName());
            return;
        }

        employeeDao.updateEmployee(employee);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        logger.info("deleteById: id - " + id);
        Employee employeeWithId = findById(id);
        if (employeeWithId == null) {
            logger.info("No employee with id - " + id + " found.");
            return;
        }
        employeeDao.deleteById(id);
    }
}
