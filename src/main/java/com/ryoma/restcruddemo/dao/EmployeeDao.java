package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.dao.exception.EmployeeDataDuplicatesFoundException;

import java.util.List;

public interface EmployeeDao {

    List<Employee> findAll();

    Employee findById(int id);

    /**
     * Search for the only one matched record's id having the {@code employee}'s data.
     *
     * @param employee record to search for.
     * @return the matched record's id. If no matches is found, -1 is returned.
     * @throws EmployeeDataDuplicatesFoundException if multiple matched records are found.
     */
    int findIdByInfo(Employee employee) throws EmployeeDataDuplicatesFoundException;

    /**
     * Search for the only one matched record with the specified {@code firstName} and {@code lastName}.
     *
     * @param firstName employee's firstName, cannot be null
     * @param lastName employee's lastName, cannot be null
     * @return the matched record. If no matches is found, null {@code Employee} is returned.
     * @throws EmployeeDataDuplicatesFoundException if duplicated full names are found on multiple records
     */
    Employee findByFullName(String firstName, String lastName) throws EmployeeDataDuplicatesFoundException;

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);

    Employee findByEmail(String email);

    /**
     * Add a new record to the database. An id will be generated as its primary key. A validation will be performed
     * to make sure of non-duplicate record insertion.
     *
     * @param employee record to be inserted
     * @return the primary key of the new record
     * @throws EmployeeDatabaseException if the queried {@code employee} resembles an existing record found in the database
     */
    int addEmployee(Employee employee) throws EmployeeDatabaseException;

    void updateEmployee(Employee employee);

    void deleteById(int id);
}
