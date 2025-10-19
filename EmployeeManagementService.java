package service;

import model.Employee;

import java.lang.reflect.Array;
import java.util.*;

public class EmployeeManagementService {

    ArrayList<Employee> listOfEmp = new ArrayList<>();

    Employee emp1 = new Employee(1,"Anuj","HR",20000);

    Employee emp2 = new Employee(2,"Anurag","Technical",30000);

    Employee emp3 = new Employee(3,"Fox","Sales",40000);


    public EmployeeManagementService() {
        listOfEmp.add(emp1);
        listOfEmp.add(emp2);
        listOfEmp.add(emp3);
    }

    public ArrayList<Employee> getAllEmployees(){

        return listOfEmp;
    }

    public void addEmployee(Employee emp){

        listOfEmp.add(emp);

    }

    public void remove(Employee emp){

        listOfEmp.remove(emp);
    }



}
