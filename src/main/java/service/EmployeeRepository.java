package service;

import java.util.List;

public interface EmployeeRepository {

	List<Employee> findAll();

	Employee get(String id);

    void put(Employee employee);

}
