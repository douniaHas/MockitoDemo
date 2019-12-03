package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.BankService;
import service.EmployeeManager;
import service.EmployeeRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//TODO :pk ça marche alors les tests precedents throws exception ! reset mock à chaque debut de test?
@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerSuiteTest2 {
        @Spy
        @InjectMocks
        private EmployeeManager employeeManager;
        @Mock
        private EmployeeRepository employeeRepository;
        @Mock
        private BankService bankService;


    @Test(expected = RuntimeException.class)
    public void resetting_mocks_17_partII(){
        when(employeeRepository.findAll()).thenThrow(new RuntimeException());
        employeeManager.payEmployees();
    }

    @Test(expected = RuntimeException.class)
    public void stub_void_method_with_exception_5(){
        when(employeeRepository.findAll()).thenThrow(new RuntimeException());
        employeeManager.payEmployees();
    }

    @Test
    public void resetting_mocks_17_partII_test2(){
        assertEquals(0,employeeManager.payEmployees());
    }
}
