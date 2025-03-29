package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.entity.EmployeeRowMapper;
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

    @Autowired
    public EmployeeDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            System.out.println("jdbcTemplate bean was not registered!!!");
        } else {
            System.out.println("jdbcTemplate bean was registered successfully!!!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findAll()");
        String query = "SELECT * FROM employee";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
    }

    @Override
    public Employee findById(int id) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findById(" + id + ")");
        String query = "SELECT * FROM employee WHERE id=?";
        return jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), id);
    }

    @Override
    public int findIdByInfo(Employee employee) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByInfo(): " + employee);
        String query = "SELECT * FROM employee WHERE first_name=? AND last_name=? AND email=?";
        Employee employeeRetrieved = jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
        return employeeRetrieved.getId();
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByFirstName(): " + firstName);
        String query = "SELECT * FROM employee WHERE first_name=?";
        return jdbcTemplate.query(query, new EmployeeRowMapper(), firstName);
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByLastName(): " + lastName);
        String query = "SELECT * FROM employee WHERE last_name=?";
        return jdbcTemplate.query(query, new EmployeeRowMapper(), lastName);
    }

    @Override
    public List<Employee> findByEmail(String email) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findByEmail(): " + email);
        String query = "SELECT * FROM employee WHERE email=?";
        return jdbcTemplate.query(query, new EmployeeRowMapper(), email);
    }

    @Override
    public int addEmployee(Employee employee) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".addEmployee(): " + employee);
        String query = "INSERT INTO employee_directory.employee (first_name, last_name, email) VALUES (?,?,?)";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail());
        return findIdByInfo(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".updateEmployee(): " + employee);
        String query = "UPDATE employee_directory.employee SET first_name=?, last_name=?, email=?";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail());
    }

    @Override
    public void deleteById(int id) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".deleteById(" + id + ")");
        String query = "DELETE FROM employee WHERE id=?";
        jdbcTemplate.update(query, id);
    }
}
