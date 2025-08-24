package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.entity.EmployeeRowMapper;
import com.ryoma.restcruddemo.dao.exception.EmployeeDataDuplicatesFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class EmployeeDaoJdbcTemplateImpl implements EmployeeDao {
    private final JdbcTemplate jdbcTemplate;
    private List<Employee> list;
    private Logger logger;

    @Autowired
    public EmployeeDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        init();
        if (jdbcTemplate == null) {
            logger.info("jdbcTemplate bean was not registered!!!");
        } else {
            logger.info("jdbcTemplate bean was registered successfully!!!");
        }
    }

    private void init() {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getSimpleName() + ".init()");
//        logger = LogManager.getLogger(EmployeeDaoJdbcTemplateImpl.class);
        logger = LogManager.getLogger();
        if (logger == null) {
            logger.info("Something went wrong when creating logger for " + EmployeeDaoJdbcTemplateImpl.class.getName());
            throw new RuntimeException("logger cannot be created");
        }
        logger.info("logger's name: " + logger.getName());
    }

    @Override
    public List<Employee> findAll() {
        logger.info("findAll()");
        String query = "SELECT * FROM employee";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
    }

    @Override
    public Employee findById(int id) {
        logger.info("findById(" + id + ")");
        String query = "SELECT * FROM employee WHERE id=?";
        List<Employee> employeeMatches = jdbcTemplate.query(query, new EmployeeRowMapper(), id);
        if (employeeMatches.isEmpty()) {
            logger.info("No employee found with the given id");
            return null;
        }
        logger.info("Found an Employee with the id: " + employeeMatches.get(0));
        return employeeMatches.get(0);
//        return jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), id);
    }

    @Override
    public int findIdByInfo(Employee employee) throws EmployeeDataDuplicatesFoundException {
        logger.info("findByInfo(): " + employee);

        String query = "SELECT * FROM employee WHERE first_name=? AND last_name=? AND email=?";
        List<Employee> employeeList = jdbcTemplate.query(query, new EmployeeRowMapper(), employee.getFirstName(), employee.getLastName(), employee.getEmail());

        if (employeeList.isEmpty()) {
            return -1;
        }
        if (employeeList.size() > 1) {
            List<Integer> idList = employeeList.stream()
                    .map(Employee::getId)
                    .toList();
            throw new EmployeeDataDuplicatesFoundException("Found multiple duplicates on records with id's: " + idList);
        }

        return employeeList.get(0).getId();
    }

    @Override
    public Employee findByFullName(String firstName, String lastName) throws EmployeeDataDuplicatesFoundException {
        logger.info("findByFullName(): " + firstName + " " + lastName);

        String query = "SELECT * FROM employee WHERE first_name=? AND last_name=?";
        List<Employee> employeeList = jdbcTemplate.query(query, new EmployeeRowMapper(), firstName, lastName);

        if (employeeList.isEmpty()) {
            return null;
        }
        if (employeeList.size() > 1) {
            List<Integer> idList = employeeList.stream()
                    .map(Employee::getId)
                    .toList();
            throw new EmployeeDataDuplicatesFoundException("Found duplicated full names on records with id's: " + idList);
        }

        return employeeList.get(0);
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        logger.info("findByFirstName(): " + firstName);
        String query = "SELECT * FROM employee WHERE first_name=?";
        return jdbcTemplate.query(query, new EmployeeRowMapper(), firstName);
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        logger.info("findByLastName(): " + lastName);
        String query = "SELECT * FROM employee WHERE last_name=?";
        return jdbcTemplate.query(query, new EmployeeRowMapper(), lastName);
    }

    @Override
    public Employee findByEmail(String email) {
        logger.info("findByEmail(): " + email);
        String query = "SELECT * FROM employee WHERE email=?";
        List<Employee> employeeMatches = jdbcTemplate.query(query, new EmployeeRowMapper(), email);
        if (employeeMatches.isEmpty()) {
            logger.info("No employee found with the given email");
            return null;
        }
        logger.info("Found an Employee with the email: " + employeeMatches.get(0));
        return employeeMatches.get(0);
    }

    @Override
    @Transactional
    public int addEmployee(Employee employee) {
        logger.info("addEmployee(): " + employee);
        String query = "INSERT INTO employee_directory.employee (first_name, last_name, email) VALUES (?,?,?)";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail());

        int id;
        try {
            id = findIdByInfo(employee);
        } catch (EmployeeDataDuplicatesFoundException e) {
            logger.info("Cannot insert duplicated employee");
            return -1;
        }

        return id;
    }

    @Override
    public void updateEmployee(Employee employee) {
        logger.info("updateEmployee(): " + employee);
        String query = "UPDATE employee_directory.employee SET first_name=?, last_name=?, email=? WHERE id=?";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getId());
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById(" + id + ")");
        String query = "DELETE FROM employee WHERE id=?";
        jdbcTemplate.update(query, id);
    }
}
