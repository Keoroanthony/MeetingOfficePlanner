package com.tracom.mop.Service;

import com.tracom.mop.Entity.Employee;
import com.tracom.mop.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository= employeeRepository;

    }
    //CREATE
    public void saveUser(Employee employee){
        employeeRepository.save(employee);
    }

    //RETRIEVE
    public Employee getUserByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    public List<Employee> listUsers(){
        return employeeRepository.findAll();
    }
    public List<Employee> getAllEmailByOrganization(int id) {
        return employeeRepository.findAllEmailByOrganization(id);
    }

    // DELETE
    public void deleteUser(int id){
        employeeRepository.deleteById(id);
    }

    //UPDATE
    public void updateUserDetails(int Id,
                                  String employee_name,
                                  String phone){
        employeeRepository.updateUserDetails(Id, employee_name,phone);
    }
    public Employee updateUser(int id){
        return employeeRepository.findById(id).get();
    }
}
