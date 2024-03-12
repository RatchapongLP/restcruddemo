package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> findAll();
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteById(int id);
}
