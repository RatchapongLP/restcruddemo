package com.ryoma.restcruddemo.dao.exception;

public class EmployeeDataDuplicatesFoundException extends Exception{

    public EmployeeDataDuplicatesFoundException(String s) {
        super(s);
    }
}
