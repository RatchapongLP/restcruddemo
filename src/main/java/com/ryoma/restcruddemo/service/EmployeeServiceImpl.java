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
        System.out.println(EmployeeServiceImpl.class.getSimpleName() + ".init()");
        logger = LogManager.getLogger(EmployeeServiceImpl.class);
//        logger = LogManager.getLogger();
        if (logger == null) {
            System.out.println("Something went wrong when creating logger for " + EmployeeServiceImpl.class.getName());
        }
        logger.info("logger's name: " + logger.getName());
    }

    @Override
    public Employee findById(int id) {
        logger.info("findById: id - " + id);
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> findEmployees(Employee employee) throws Exception {
        logger.info("findEmployees: employee - {}", employee);

        if (employee == null) {
            return employeeDao.findAll();
        }

        // TODO: implements all the scenarios of available (non-null) fields with least db access

        if (employee.getFirstName() != null && employee.getLastName() != null && employee.getEmail() != null) {

            logger.info("Searching the employee with the queried firstName, lastName, and email");

            int idResult;
            try {
                idResult = employeeDao.findIdByInfo(employee);
            } catch (Exception e) {
                logger.error("{}: {}", EmployeeServiceImpl.class.getSimpleName(), e.getMessage());
                throw e;
            }
            if (idResult == -1) return null;

            logger.info("Found a matched record with the id - {}", idResult);
            logEmployeeIdChecking(employee, idResult);

            // Patch the correct id for the special case.
            employee.setId(idResult);

            return List.of(employee);
        }

        if (employee.getFirstName() != null && employee.getLastName() != null) {

            logger.info("Searching the employee with the queried firstName and lastName");

            Employee employeeResult;
            try {
                employeeResult = employeeDao.findByFullName(employee.getFirstName(), employee.getLastName());
            } catch (Exception e) {
                logger.error("{}: {}", EmployeeServiceImpl.class.getSimpleName(), e.getMessage());
                throw e;
            }
            if (employeeResult == null) return null;
            logEmployeeIdChecking(employee, employeeResult.getId());

            return List.of(employeeResult);
        }

        if (employee.getEmail() != null) {

            logger.info("Searching the employee with the queried email");

            Employee employeeResult;
            try {
                employeeResult = employeeDao.findByEmail(employee.getEmail());
            } catch (Exception e) {
                logger.error("{}: {}", EmployeeServiceImpl.class.getSimpleName(), e.getMessage());
                throw e;
            }
            if (employeeResult == null) return null;
            logEmployeeIdChecking(employee, employeeResult.getId());

            return List.of(employeeResult);
        }

        if (employee.getFirstName() != null) {

            logger.info("Searching the employee with the queried firstName");

            List<Employee> employeeResultList;
            try {
                employeeResultList = employeeDao.findByFirstName(employee.getFirstName());
            } catch (Exception e) {
                logger.error("{}: {}", EmployeeServiceImpl.class.getSimpleName(), e.getMessage());
                throw e;
            }

            return employeeResultList;
        }

        if (employee.getLastName() != null) {

            logger.info("Searching the employee with the queried lastName");

            List<Employee> employeeResultList;
            try {
                employeeResultList = employeeDao.findByLastName(employee.getLastName());
            } catch (Exception e) {
                logger.error("{}: {}", EmployeeServiceImpl.class.getSimpleName(), e.getMessage());
                throw e;
            }

            return employeeResultList;
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

    /**
     * Optimized by remove redundant update if the queried data is the same.
     * @param employee record to update in the database. All the fields of the queried employee will replace the original ones.
     */
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

        // Can save a redundant db update.
        if (employeeWithFullName != null && employeeWithEmail != null) {
            logger.info("Queried employee has the same data, early quit updating in " + EmployeeServiceImpl.class.getName());
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

    private void logEmployeeIdChecking(Employee queriedEmployee, int databaseId) {
        if (queriedEmployee.getId() != 0 && queriedEmployee.getId() != databaseId) {
            logger.info("The queried employee's id conflicts with the database.");
        } else {
            logger.info("No conflict found.");
        }
    }
}
