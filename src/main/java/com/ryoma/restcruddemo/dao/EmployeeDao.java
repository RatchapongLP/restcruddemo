package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> findAll();

    Employee findById(int id);

    int findIdByInfo(Employee employee);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);

    List<Employee> findByEmail(String email);

    int addEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteById(int id);
}
