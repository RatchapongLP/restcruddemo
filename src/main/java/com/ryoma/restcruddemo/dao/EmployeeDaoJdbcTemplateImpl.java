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
//        this.list = new ArrayList<>();
//        list.addAll(List.of(new Employee("Ryoma", "Echizen", "ryomano1@gmail.com"),
//                new Employee("Fuji", "Shuzuke", "fujirestaurant@gmail.com"),
//                new Employee("Ham", "Taro", "hamtaro@gmail.com")));
    }

    @Override
    public List<Employee> findAll() {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findAll()");
        String query = "SELECT * FROM employee";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
//        return List.copyOf(this.list);
    }

    @Override
    public Employee findById(int id) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".findById(" + id + ")");
        String query = "SELECT * FROM employee WHERE id=?";
        return jdbcTemplate.queryForObject(query,new EmployeeRowMapper(), id);
//        return this.list.stream().filter(e -> e.getId() == id).findAny().orElseThrow();
    }

    @Override
    public Employee save(Employee employee) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".save(): " + employee);
        String query = "INSERT INTO employee_directory.employee (first_name, last_name, email) VALUES (?,?,?)";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail());
//        this.list.add(employee);
        return employee;
    }

    @Override
    public void deleteById(int id) {
        System.out.println(EmployeeDaoJdbcTemplateImpl.class.getName() + ".deleteById(" + id + ")");
        String query = "DELETE FROM employee WHERE id=?";
        jdbcTemplate.update(query, id);
//        this.list = this.list.stream().filter(e -> e.getId() != id).collect(Collectors.toList());
    }
}
