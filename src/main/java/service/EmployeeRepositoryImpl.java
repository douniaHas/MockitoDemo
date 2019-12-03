package service;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository{

    List<Employee> employees = new ArrayList<Employee>();

    public List<Employee> findAll(){
        return employees;
    }

    public void put(Employee employee){
        employees.add(employee);
    }

    public Employee get(String id){
        for (Employee e: employees) {
            if(id.equals(e.getId())){
                return e;
            }
        }
        return null;
    }
}
