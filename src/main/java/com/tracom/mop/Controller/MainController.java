package com.tracom.mop.Controller;

import com.tracom.mop.CustomEmployeeDetails;
import com.tracom.mop.EmployeeNotFoundException;
import com.tracom.mop.Entity.*;
import com.tracom.mop.Service.*;
import com.tracom.mop.Utility.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
public class MainController {

    private EmployeeService employeeService;
    private OrganizationService organizationService;
    private DepartmentService departmentService;
    private RoleService roleService;
    private ResourcesService resourcesService;
    private RoomService roomService;
    private MeetingService meetingService;
    private JavaMailSender mailSender;

    @Autowired
    public MainController(EmployeeService employeeService,
                          OrganizationService organizationService,
                          DepartmentService departmentService,
                          RoleService roleService,
                          ResourcesService resourcesService,
                          RoomService roomService,
                          JavaMailSender mailSender,
                          MeetingService meetingService){
        this.employeeService = employeeService;
        this.organizationService = organizationService;
        this.departmentService = departmentService;
        this.roleService = roleService;
        this.resourcesService = resourcesService;
        this.roomService = roomService;
        this.mailSender = mailSender;
        this.meetingService = meetingService;
    }

    /********                  INDEX PAGE                         ********/
    @GetMapping("")
    public String viewHomePage() {
        return ("index");
    }



    /********                  LOGIN PAGE                         ********/
    @GetMapping(value = "/login")
    public String login (){
        return "login";
    }


    /********                  LOGOUT PAGE                         ********/
    @GetMapping("/logout")
    public String logOutPage() {
        return ("index");
    }


    /********                  HOME PAGE                         ********/
    @GetMapping("/home")
    public String homePage(@AuthenticationPrincipal CustomEmployeeDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        Employee employee = employeeService.getUserByEmail(email);
        int id = employee.getOrganization().getId();

        List<Meeting> meetingList = meetingService.getAllByOrganization(id);
        List<Meeting> todayMeetings = meetingService.getOrganizationMeetingsToday(id);

        model.addAttribute("loggedUser", employee);
        model.addAttribute("meetingList", meetingList);
        model.addAttribute("todayMeetings", todayMeetings);
        return ("home");
    }


    /********                  CALENDAR PAGE                         ********/
    @GetMapping("/calendar")
    public String calendarPage() {
        return ("calendar");
    }
    @GetMapping("/403")
    public String accessDeniedPage() {
        return ("error_403");
    }



    /********                  CRUD USERS                          ********/

    @GetMapping("/users")
    public String showUserList(@AuthenticationPrincipal CustomEmployeeDetails loggedUser, Model model){

        String email = loggedUser.getUsername();
        Employee employee = employeeService.getUserByEmail(email);
        int id = employee.getOrganization().getId();

        List<Employee> usersList = employeeService.getAllEmailByOrganization(id);
        List<Employee> listUsers =employeeService.listUsers();

        model.addAttribute("usersList", usersList);
        model.addAttribute("listUsers", listUsers);
        return "users";

    }
    @GetMapping("/add_user")
    public String addUser(Model model){
        List<Organization> organizationList = organizationService.listOrganization();
        List<Department> departmentList = departmentService.listDepartment();
        List<Role> roleList = roleService.listRoles();
        model.addAttribute("user", new Employee());
        model.addAttribute("organizations", organizationList);
        model.addAttribute("departments", departmentList);
        model.addAttribute("roles", roleList);
        return "add_users";
    }
    @PostMapping("/users_save")
    public String saveUser(Employee employee){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employee.setEnabled(true);
        employeeService.saveUser(employee);
        return "redirect:/users";
    }
    @RequestMapping("/edit_user/{id}")
    public ModelAndView showCreateUser(@PathVariable(name = "id") int id){

        ModelAndView mnv = new ModelAndView("edit_user");

        //User object
        Employee employee = employeeService.updateUser(id);
        mnv.addObject("editUser", employee);

        return mnv;
    }
    @GetMapping( "/profile")
    public String profilePage(@AuthenticationPrincipal CustomEmployeeDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        Employee employee = employeeService.getUserByEmail(email);

        model.addAttribute("loggedUser", employee);
        return "profile";
    }
    @PostMapping(path = "/edit_profile/update")
    public String updateUserProfile(@AuthenticationPrincipal CustomEmployeeDetails loggedUser,
                                    @RequestParam(value = "Id", required = false) int Id,
                                    @RequestParam(value = "employee_name", required = false) String employee_name,
                                    @RequestParam(value = "phone", required = false) String phone,
                                    Employee employee,
                                    RedirectAttributes redirectAttributes){

        //Perform update
        employeeService.updateUserDetails(Id, employee_name, phone);

        //Set current loggedIn user details on top.
       loggedUser.setEmployeeName(employee_name);
        return "redirect:/profile";
    }
    @RequestMapping("/delete_user/{id}")
    public String deleteService(@PathVariable(name = "id") int id) {
        employeeService.deleteUser(id);
        return "redirect:/users";
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




    /********                  CRUD DEPARTMENT                           ********/
    @GetMapping("/department")
    public String showDepartmentList(Model model){
        List<Department> departmentList =departmentService.listDepartment();
        model.addAttribute("departmentList", departmentList);
        return "add_department";
    }
    @GetMapping("/add_department")
    public String showNewDepartment(Model model){
        model.addAttribute("department", new Department());
        List<Organization> organizationList = organizationService.listOrganization();
        model.addAttribute("organizations", organizationList);
        return "add_department";
    }
    @PostMapping("/department_save")
    public String saveDepartment(Department department) {
        departmentService.saveDepartment(department);
        return "add_department";
    }



    /********                  CRUD MEETINGS                           ********/
    @GetMapping("/meetings")
    public String meetingsPage(@AuthenticationPrincipal CustomEmployeeDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        Employee employee = employeeService.getUserByEmail(email);
        int id = employee.getOrganization().getId();
        List<Meeting> meetingList = meetingService.getAllByOrganization(id);
        model.addAttribute("meetingList", meetingList);
        return ("meetings");
    }
    @GetMapping("/add_meeting")
    public String newMeeting(@AuthenticationPrincipal CustomEmployeeDetails loggedUser, Model model){
        String email = loggedUser.getUsername();
        Employee employee = employeeService.getUserByEmail(email);
        int id = employee.getOrganization().getId();

        model.addAttribute("meeting", new Meeting());
        List<Employee> employeeList = employeeService.getAllEmailByOrganization(id);
        List<Room> roomList = roomService.getAllByOrganization(id);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("roomList", roomList);
        return ("add_meeting");
    }
    @PostMapping("/meeting_save")
    public String saveRoom(@AuthenticationPrincipal CustomEmployeeDetails loggedUser,Meeting meeting) {
        String email = loggedUser.getUsername();
        Employee employee = employeeService.getUserByEmail(email);
        int employeeId = employee.getId();
        meeting.setOwner(employeeId);
        meetingService.saveMeeting(meeting);
        return "redirect:/meetings";
    }




    /********                  CRUD ROOMS                           ********/
    @GetMapping("/rooms")
    public String roomPage(@AuthenticationPrincipal CustomEmployeeDetails loggedUser, Model model){
        String email = loggedUser.getUsername();
        Employee employee = employeeService.getUserByEmail(email);
        int id = employee.getOrganization().getId();
        List<Room> roomList = roomService.getAllByOrganization(id);

        model.addAttribute("roomList", roomList);
        return ("rooms");
    }
    @GetMapping("/add_room")
        public String newRoom(Model model){
        model.addAttribute("room", new Room());
        List<Organization> organizationList = organizationService.listOrganization();
        List<Resources> resourcesList = resourcesService.listResources();
        model.addAttribute("organizations", organizationList);
        model.addAttribute("resources", resourcesList);

        return "add_room";
        }

    @PostMapping("/room_save")
    public String saveRoom(Room room) {
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }



    /********                  FORGOT PASSWORD                          ********/

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try {
            employeeService.updateResetPasswordToken(token, email);

            //generate reset pwd link
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;


            sendEmail(email, resetPasswordLink);
            //send email
            model.addAttribute("message", "We have sent a password reset link your email.");

        } catch (EnumConstantNotPresentException | MessagingException | UnsupportedEncodingException | EmployeeNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "forgot_password";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("anton.maoga@gmail.com", "Meeting Office Planner");
        helper.setTo(email);

        String subject = "Password Reset - Meeting Office Planner";

        String content = "<p>Hi,</p>"
                + "<p>Here is your requested reset password link</p>"
                + "<p>Click and follow instructions to reset password</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>"
                + "<p>Kindly ignore the email if you didn't request for a password reset.</p>"
                + "<p>Regards,</p>"
                + "<p>Meeting Office Planner</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }



    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model){
        Employee employee = employeeService.getUserByToken(token);

        if (employee == null){
            model.addAttribute("message", "Invalid Token");
        }

        model.addAttribute("token", token);

        return "reset_password";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model){
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Employee user = employeeService.getUserByToken(token);

        if (user == null){
            model.addAttribute("messageErr", "Invalid Token");
        }
        else {
            employeeService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password");
        }

        model.addAttribute("token", token);

        return "login";
    }
}



