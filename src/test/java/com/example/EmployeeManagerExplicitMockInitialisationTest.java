package com.example;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import service.EmployeeManager;
import service.EmployeeRepository;

import static org.mockito.Mockito.*;

public class EmployeeManagerExplicitMockInitialisationTest {
    
    private EmployeeManager employeeManager; 
    
    private EmployeeRepository employeeRepository;

    @Ignore
    @Test
    public void verify_interactions_1(){
        EmployeeRepository employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
        when(employeeRepositoryMock.findAll()).thenThrow(new RuntimeException());

        EmployeeManager employeeManagerSpy = Mockito.spy(EmployeeManager.class);
        //TODO : comment injecter les mocks dans le spy?
        verify(employeeRepositoryMock, times(1));
    }
}
