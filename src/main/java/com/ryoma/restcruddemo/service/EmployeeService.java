package com.ryoma.restcruddemo.service;

import com.ryoma.restcruddemo.entity.Employee;
import com.ryoma.restcruddemo.exception.EmployeeDatabaseException;

import java.util.List;

public interface EmployeeService {

//    /**
//     * Retrieves all the employee records in the database.
//     *
//     * @return {@link List} containing all the {@code employee}s found
//     */
//    List<Employee> findAll();

    /**
     * Retrieves an employee record in the database.
     *
     * @param id primary key of the record to search for in the database
     * @return {@link Employee} found with the corresponding {@code id}, {@code null} if none is found
     */
    Employee findById(int id);

    /**
     * Retrieves a list of employee records of which some, or all
     * properties are identical to the queried employee's.
     * <p>
     * Rules:
     * <ol>
     *     <li>If {@code employee} is null, all the records will be returned by the method.</li>
     *     <li>All of the {@code employee}'s non-null fields will be used in the search.</li>
     * </ol>
     * </p>
     * Special case: if {@code employee} is provided with all fields as non-null and the matched record
     * is retrieved from the database but with different id, the existing record's id will replace the
     * queried's id and the patched {@code employee} will be returned in a list.
     *
     * @param employee contains the properties that will be searched for record matches
     * @return {@link List} containing all the matched records
     * @throws EmployeeDatabaseException if duplicate records with different id's are found
     */
    List<Employee> findEmployees(Employee employee) throws EmployeeDatabaseException;

//    /**
//     * Retrieves an employee record in the database.
//     *
//     * @param employee record to search for in the database
//     * @return primary key of the record found, {@code -1} if none is matched
//     */
//    int findIdByInfo(Employee employee);

//    /**
//     * Retrieves an employee record in the database.
//     *
//     * @param firstName first_name value of the record to search for in the database
//     * @return {@link List} of {@link Employee} found with the corresponding {@code firstName}, {@code null} if none is found
//     */
//    List<Employee> findByFirstName(String firstName);
//
//    /**
//     * Retrieves an employee record in the database.
//     *
//     * @param lastName last_name value of the record to search for in the database
//     * @return {@link List} of {@link Employee} found with the corresponding {@code lastName}, {@code null} if none is found
//     */
//    List<Employee> findByLastName(String lastName);
//
//    /**
//     * Retrieves an employee record in the database.
//     * @param email of the record to search for in the database
//     * @return {@link List} of {@link Employee} found with the corresponding {@code email}, {@code null} if none is found
//     */
//    List<Employee> findByEmail(String email);

    /**
     * Creates a new employee record in the database.
     * The queried employee cannot have the same first name, last name, and email as those of an existing one.
     * Validations are to be performed on those properties, preventing repeated records.
     *
     * @return auto-generated primary key of the created employee as an integer
     * @throws IllegalArgumentException if the data is found duplicating another employee
     * */
    int addEmployee(Employee employee) throws EmployeeDatabaseException;

    /**
     * Modifies an existing employee record in the database.
     * Only employee with existing id can be updated without failure. If there exists a record with the same full name or email,
     * but with different id, the update will fail.
     *
     * @param employee record to update in the database. All the fields of the queried employee will replace the original ones.
     * @throws IllegalArgumentException if the data is found duplicating another employee or the id cannot be not found in the database
     */
    void updateEmployee(Employee employee);

    /**
     * Deletes an existing employee record in the database.
     *
     * @param id primary key of the employee to delete in the database
     * @throws IllegalArgumentException if the id cannot be not found in the database
     */
    void deleteById(int id);
}
