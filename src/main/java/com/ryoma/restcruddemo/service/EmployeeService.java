package com.ryoma.restcruddemo.service;

import com.ryoma.restcruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Retrieves all the employee records in the database.
     *
     * @return {@link List} containing all the {@code employee}s found
     */
    List<Employee> findAll();

    /**
     * Retrieves an employee record in the database.
     *
     * @param id primary key of the record to search for in the database
     * @return {@link Employee} found with the corresponding {@code id}, {@code null} if none is found
     */
    Employee findById(int id);

    /**
     * Retrieves an employee record in the database.
     *
     * @param employee record to search for in the database
     * @return primary key of the record found, {@code -1} if none is matched
     */
    int findIdByInfo(Employee employee);

    /**
     * Retrieves an employee record in the database.
     *
     * @param firstName first_name value of the record to search for in the database
     * @return {@link List} of {@link Employee} found with the corresponding {@code firstName}, {@code null} if none is found
     */
    List<Employee> findByFirstName(String firstName);

    /**
     * Retrieves an employee record in the database.
     *
     * @param lastName last_name value of the record to search for in the database
     * @return {@link List} of {@link Employee} found with the corresponding {@code lastName}, {@code null} if none is found
     */
    List<Employee> findByLastName(String lastName);

    /**
     * Retrieves an employee record in the database.
     *
     * @param email email value of the record to search for in the database
     * @return {@link List} of {@link Employee} found with the corresponding {@code email}, {@code null} if none is found
     */
    List<Employee> findByEmail(String email);

    /**
     * Creates a new employee record in the database.
     * A new employee should not have the same first name and last name as those of an existing one.
     * Replicating email is also forbidden.
     * A validation will be performed on those properties, preventing repeating records.
     *
     * @return auto-generated primary key of the created employee
     * {@code -1} if the {@code employee} validation fails
     * @throws RuntimeException if the validation succeeds but the creation fails
     */
    int addEmployee(Employee employee);

    /**
     * Modifies an existing employee record in the database.
     *
     * @param employee record to update in the database. All the properties of the passed object will replace the original ones.
     * @throws RuntimeException if the update of the employee record fails
     */
    void updateEmployee(Employee employee);

    /**
     * Deletes an existing employee record in the database.
     *
     * @param id primary key of the employee to delete in the database
     * @throws RuntimeException if the deletion of the record with {@code id} fails
     */
    void deleteById(int id);
}
