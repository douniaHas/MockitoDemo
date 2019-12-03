package service;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class EmployeeManager {

	private static final Logger LOGGER = LogManager.getLogger(EmployeeManager.class);
	@Inject
	private EmployeeRepository employeeRepository;
	@Inject
	private BankService bankService;

	//public EmployeeManager(){};
    //injection de dépendances
	/*
	public EmployeeManager(EmployeeRepository employeeRepository, BankService bankService) {
		this.employeeRepository = employeeRepository;
		this.bankService = bankService;
	}*/
/*
    public EmployeeManager() {

    }*/

    public int payEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		int payments = 0;

			for (Employee employee : employees) {
				try {
					double amount = payEmployeesImpl(employee);
					employeeRepository.put(employee);
					printTransaction(employee);
					payments += amount;
				} catch (RuntimeException e) {
					LOGGER.error("Failed payment of " + employee, e);
					employee.setPaid(false);
					throw new RuntimeException(e);
				}
			}

		return payments;
	}
	public final double printTransaction(Employee employee){
    	return employee!=null ? 1d: 0d;
	}

    //TODO : how mock private method ??
    public double payEmployeesImpl(Employee employee) {
        employee.setPaid(true);
        return bankService.pay(employee.getId(), employee.getSalary());
    }

}
