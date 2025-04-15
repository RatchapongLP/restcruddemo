package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.entity.EmployeeRowMapper;
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
        logger = LogManager.getLogger(EmployeeDaoJdbcTemplateImpl.class.getSimpleName());
        if (logger == null) {
            logger.info("Something went wrong when creating logger for " + EmployeeDaoJdbcTemplateImpl.class.getName());
        }
    }

    @Override
    public List<Employee> findAll() {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findAll()");
        String query = "SELECT * FROM employee";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
    }

    @Override
    public Employee findById(int id) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findById(" + id + ")");
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
    public int findIdByInfo(Employee employee) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByInfo(): " + employee);
        String query = "SELECT * FROM employee WHERE first_name=? AND last_name=? AND email=?";
        Employee employeeRetrieved = jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
        if (employeeRetrieved != null) {
            return employeeRetrieved.getId();
        }
        return -1;
    }

    @Override
    public Employee findByFullName(String firstName, String lastName) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByFullName(): " + firstName + " " + lastName);
        String query = "SELECT * FROM employee WHERE first_name=? AND last_name=?";
        List<Employee> employeeMatches = jdbcTemplate.query(query, new EmployeeRowMapper(), firstName, lastName);
        if (employeeMatches.isEmpty()) {
            logger.info("No employee found with given the fullName");
            return null;
        }
        logger.info("Found an Employee with the fullName: " + employeeMatches.get(0));
        return employeeMatches.get(0);
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByFirstName(): " + firstName);
        String query = "SELECT * FROM employee WHERE first_name=?";
        return jdbcTemplate.query(query, new EmployeeRowMapper(), firstName);
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByLastName(): " + lastName);
        String query = "SELECT * FROM employee WHERE last_name=?";
        return jdbcTemplate.query(query, new EmployeeRowMapper(), lastName);
    }

    @Override
    public Employee findByEmail(String email) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByEmail(): " + email);
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
    public int addEmployee(Employee employee) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".addEmployee(): " + employee);
        String query = "INSERT INTO employee_directory.employee (first_name, last_name, email) VALUES (?,?,?)";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail());
        return findIdByInfo(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".updateEmployee(): " + employee);
        String query = "UPDATE employee_directory.employee SET first_name=?, last_name=?, email=? WHERE id=?";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getId());
    }

    @Override
    public void deleteById(int id) {
        logger.info(EmployeeDaoJdbcTemplateImpl.class.getName() + ".deleteById(" + id + ")");
        String query = "DELETE FROM employee WHERE id=?";
        jdbcTemplate.update(query, id);
    }
}
