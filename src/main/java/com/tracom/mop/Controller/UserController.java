package com.tracom.mop.Controller;


import com.tracom.mop.Entity.Department;
import com.tracom.mop.Entity.Employee;
import com.tracom.mop.Entity.Organization;
import com.tracom.mop.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
    public String saveUser(Employee employee, Model model)throws MessagingException, UnsupportedEncodingException {

        String email = employee.getEmail();
        String name = employee.getEmployee_name();

        employeeService.saveUser(employee);
        model.addAttribute("message", "Registration Successful");
        registerEmail(email, name);
        return "login";
    }

    private void registerEmail(String email, String name) throws MessagingException, UnsupportedEncodingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("staff@mop.com", "Meeting Office Planner");
        helper.setTo(email);

        String subject = "Meeting Office Planner - Await Verification";

        String content = "<p>Hi " + name + ",</p>"
                + "<p>You have created an account with us.</p>"
                + "<p>Please await your verification to continue using our services</p>"
                + "<p>Thank you.</p><br>"
                + "<p><b>Regards,</b></p>"
                + "<p><b>Meeting Office Planner</b></p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
/***********************SET FIRST TIME PASSWORD**************************/

    @GetMapping(path = "/set_password")
    public String getSetFirstTimePasswordForm(@Param(value = "token") String token, Model model){
        Employee employee = employeeService.getUserSetPasswordByToken(token);

        if (employee == null){
            model.addAttribute("message", "User doesn't exist");
        }

        model.addAttribute("token", token);

        return "first_password";
    }

    @PostMapping("/set_password")
    public String setFirstTimePassword(HttpServletRequest request, Model model){

        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Employee employee = employeeService.getUserSetPasswordByToken(token);

        if (employee == null){
            model.addAttribute("messageErr", "Invalid Token");
        }
        else {

            employeeService.setFirstTimePassword(employee, password);
            model.addAttribute("message", "You have successfully set your password");

        }

        return "login";
    }
}
