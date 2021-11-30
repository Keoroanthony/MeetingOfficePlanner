package com.tracom.mop.Controller;


import com.tracom.mop.Entity.Department;
import com.tracom.mop.Entity.Employee;
import com.tracom.mop.Entity.Organization;
import com.tracom.mop.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private EmployeeService employeeService;
    private OrganizationService organizationService;
    private DepartmentService departmentService;
    private MeetingService meetingService;
    private JavaMailSender mailSender;

    @Autowired
    public UserController(EmployeeService employeeService,
                          OrganizationService organizationService,
                          DepartmentService departmentService,
                          JavaMailSender mailSender,
                          MeetingService meetingService){
        this.employeeService = employeeService;
        this.organizationService = organizationService;
        this.departmentService = departmentService;
        this.mailSender = mailSender;
        this.meetingService = meetingService;
    }


    @GetMapping("/register")
    public String addUser(Model model){

        List<Organization> organizationList = organizationService.listOrganization();
        List<Department> departmentList = departmentService.listDepartment();
        model.addAttribute("user", new Employee());
        model.addAttribute("organizations", organizationList);
        model.addAttribute("departments", departmentList);
        return "register";
    }
    @PostMapping("/save")
    public String saveUser(Employee employee){
        employeeService.saveUser(employee);
        return "login";
    }
}
