package com.tracom.mop.Controller;

import com.tracom.mop.Entity.Department;
import com.tracom.mop.Entity.Employee;
import com.tracom.mop.Entity.Organization;
import com.tracom.mop.Service.DepartmentService;
import com.tracom.mop.Service.EmployeeService;
import com.tracom.mop.Service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;


@Controller
public class MainController {

    private EmployeeService employeeService;
    private OrganizationService organizationService;
    private DepartmentService departmentService;

    @Autowired
    public MainController(EmployeeService employeeService, OrganizationService organizationService, DepartmentService departmentService){
        this.employeeService = employeeService;
        this.organizationService = organizationService;
        this.departmentService = departmentService;
    }


    @GetMapping("")
    public String viewHomePage() {
        return ("index");
    }


    @GetMapping(value = "/login")
    public String login (){
        return "login";
    }
    @GetMapping("/logout")
    public String logOutPage() {
        return ("index");
    }
    @GetMapping("/home")
    public String homePage() {
        return ("home");
    }
    @GetMapping("/profile")
    public String profilePage() {
        return ("profile");
    }
    @GetMapping("/calendar")
    public String calendarPage() {
        return ("calendar");
    }



    /********                  CRUD USERS                          ********/

    @GetMapping("/users")
    public String showUserList(Model model){
        List<Employee> listUsers =employeeService.listUsers();
        model.addAttribute("listUsers", listUsers);
        return "add_users";

    }
    @GetMapping("/add_user")
    public String addUser(Model model){
        List<Organization> organizations = organizationService.listOrganization();
        List<Department> departments = departmentService.listDepartment();
        model.addAttribute("user", new Employee());
        model.addAttribute("listOrganizations", organizations);
        model.addAttribute("departments", departments);
        return "add_users";
    }
    @PostMapping("/users/save")
    public String saveUser(Employee employee){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employeeService.saveUser(employee);
        return "add_users";
    }


/********                  CRUD ORGANIZATION                           ********/

    @GetMapping("/organization")
    public String showOrganizationList(Model model){
    List<Organization> listOrganization =organizationService.listOrganization();
    model.addAttribute("listOrganization", listOrganization);
    return "add_organization";

    }
    @GetMapping("/add_organization")
    public String showNewOrganization(Model model){
        model.addAttribute("organization", new Organization());
        return "add_organization";
    }
    @PostMapping("/organization/save")
    public String saveOrganization(Organization organization){
        organizationService.saveOrganization(organization);
        return "add_organization";

    }

    @GetMapping("/meetings")
    public String meetingsPage() {
        return ("meetings");
    }
}
