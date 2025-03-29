package com.ryoma.restcruddemo.dao;

import com.ryoma.restcruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoJpaImpl implements EmployeeDao {
    private EntityManager entityManager;

    @Autowired
    public EmployeeDaoJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> query = entityManager.createQuery("from Employee", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee findById(int id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public int findIdByInfo(Employee employee) {
        // TODO:
        return 0;
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        // TODO:
        return null;
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        // TODO:
        return null;
    }

    @Override
    public List<Employee> findByEmail(String email) {
        // TODO:
        return null;
    }

    @Override
    public int addEmployee(Employee employee) {
        entityManager.merge(employee);
        return entityManager.find(Employee.class, employee).getId();
    }

    @Override
    public void updateEmployee(Employee employee) {
        // TODO:
    }

    @Override
    public void deleteById(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }
}
