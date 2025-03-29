package com.ryoma.restcruddemo.service;

import com.ryoma.restcruddemo.dao.EmployeeDao;
import com.ryoma.restcruddemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeDao.findById(id);
    }

    @Override
    public int findIdByInfo(Employee employee) {
        return employeeDao.findIdByInfo(employee);
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        return null;
    }

    @Override
    public List<Employee> findByEmail(String email) {
        return null;
    }

    @Override
    @Transactional
    public int addEmployee(Employee employee) {
        return employeeDao.addEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        employeeDao.deleteById(id);
    }
}
