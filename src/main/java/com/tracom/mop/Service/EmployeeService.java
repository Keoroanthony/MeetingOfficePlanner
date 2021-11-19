package com.tracom.mop.Service;

import com.tracom.mop.EmployeeNotFoundException;
import com.tracom.mop.Entity.Employee;
import com.tracom.mop.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    //Forgot Password

    public void updateResetPasswordToken(String token, String email) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findByEmail(email);

        if (email != null){
            employee.setResetPasswordToken(token);
            employeeRepository.save(employee);
        }
        else {
            throw new EmployeeNotFoundException("Could not find user with the email " + email);
        }
    }

    public void updateSetPasswordToken(String token, String email) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findByEmail(email);

        if (employee != null){
            employee.setResetPasswordToken(token);
            employeeRepository.save(employee);
        }
        else {
            throw new EmployeeNotFoundException("Could not find user with the email " + email);
        }
    }

    public Employee getUserByToken(String token){
        return employeeRepository.findByResetPasswordToken(token);
    }


    public Employee getUserSetPasswordByToken(String token){
        return employeeRepository.findBySetPasswordToken(token);
    }

    public void updatePassword(Employee employee, String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        employee.setPassword(encodedPassword);
        employee.setResetPasswordToken(null);

        employeeRepository.save(employee);
    }


}
