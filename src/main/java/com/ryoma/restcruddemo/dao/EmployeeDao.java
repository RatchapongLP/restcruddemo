package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.exception.EmployeeDatabaseException;

import java.util.List;

public interface EmployeeDao {

    List<Employee> findAll();

    Employee findById(int id);

    /**
     * Search for the only one matched record's id having the {@code employee}'s data.
     *
     * @param employee record to search for.
     * @return the matched record's id. If no matches is found, -1 is returned.
     * @throws EmployeeDatabaseException if multiple matched records are found.
     */
    int findIdByInfo(Employee employee) throws EmployeeDatabaseException;

    Employee findByFullName(String firstName, String lastName);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);

    Employee findByEmail(String email);

    int addEmployee(Employee employee) throws EmployeeDatabaseException;

    void updateEmployee(Employee employee);

    void deleteById(int id);
}
