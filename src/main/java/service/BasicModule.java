package service;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EmployeeManager.class);
        bind(BankService.class).to(BankServiceImpl.class);
        bind(EmployeeRepository.class).to(EmployeeRepositoryImpl.class);
    }
}