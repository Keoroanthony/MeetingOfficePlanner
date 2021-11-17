package com.tracom.mop.Controller;

import com.tracom.mop.CustomEmployeeDetails;
import com.tracom.mop.Entity.*;
import com.tracom.mop.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @Autowired
    public MainController(EmployeeService employeeService,
                          OrganizationService organizationService,
                          DepartmentService departmentService,
                          RoleService roleService,
                          ResourcesService resourcesService,
                          RoomService roomService,
                          MeetingService meetingService){
        this.employeeService = employeeService;
        this.organizationService = organizationService;
        this.departmentService = departmentService;
        this.roleService = roleService;
        this.resourcesService = resourcesService;
        this.roomService = roomService;
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

        model.addAttribute("loggedUser", employee);
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
    public String saveRoom(Meeting meeting) {
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
}



