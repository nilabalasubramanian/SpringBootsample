package com.vicky.itechapp;

import com.vicky.itechapp.model.Employee;
import com.vicky.itechapp.model.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class EmployeeController {

    List<Employee> employeeList = new ArrayList<>();

    @RequestMapping(value = "/employee/all")
    List<Employee> getEmployees(){
        List<Employee> employeeList = prepareData();
        return employeeList;
    }

/*    @RequestMapping(value = "/employee/1")
    public Employee getEmployeeOne(){
        List<Employee> employeeList = prepareData();
        for(Employee employee: employeeList) {
            if(employee.getId() == 1) {
                return employee;
            }
        }
        return null;
    }*/

    /*@RequestMapping(value = "/employee/{id}")
    public Employee getEmployeeById(@PathVariable("id") Integer id){
        List<Employee> employeeList = prepareData();
        for(Employee employee: employeeList) {
            if(employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }*/

    @RequestMapping(value = "/employee/{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Integer id) {
        List<Employee> employeeList = prepareData();
        for(Employee employee: employeeList) {
            if(employee.getId() == id) {
                ResponseEntity responseEntity = new ResponseEntity(employee, HttpStatus.OK);
                return responseEntity;
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("message", "Employee not found");
        ResponseEntity responseEntity = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity createEmployee(@RequestBody Employee employee){
        addEmployee(employee);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Employee added succesfully");
        ResponseEntity responseEntity = new ResponseEntity(map, HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public ResponseEntity updateEmployee(@RequestBody Employee employee) {
        boolean updated = updateMyEmployee(employee);
        Map<String, String> map = new HashMap<>();
        ResponseEntity responseEntity;

        if(updated) {
            map.put("message", "Employee updated succesfully");
             responseEntity = new ResponseEntity(map, HttpStatus.OK);
        } else {
            map.put("message", "Employee not found for update");
            responseEntity = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }


    private List<Employee> prepareData() {

        if(employeeList.isEmpty()) {
            Employee e1 = new Employee();
            e1.setId(1);
            e1.setName("Vidhya");

            Role role = new Role();
            role.setDesignation("Chief Engineer");
            role.setDept("IT Dept");

            e1.setRole(role);

            Employee e2 = new Employee();
            e2.setId(2);
            e2.setName("Vandhana");

            Role role2 = new Role();
            role2.setDesignation("The CEO");
            role2.setDept("Management");
            e2.setRole(role2);

            employeeList.add(e1);
            employeeList.add(e2);
        }
        return employeeList;
    }

    private void addEmployee(Employee employee) {
        List<Employee> employeeList = prepareData();
        // TODO: Make sure you are checking before adding
        // TODO: Condition - Check if Employee already exist by using Id
        employeeList.add(employee);
    }

    private boolean updateMyEmployee(Employee tobeUpdatedEmployee) {
        List<Employee> employeeList = prepareData();

        Iterator<Employee> iterator = employeeList.iterator();
        boolean removed = false;

        while(iterator.hasNext()) {
            Employee employee = iterator.next();
            if(employee.getId() == tobeUpdatedEmployee.getId()) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if(removed) {
            employeeList.add(tobeUpdatedEmployee);
        }
        return removed;
    }
}
