package com.Practice.Render.Controller;

import com.Practice.Render.Model.Tickets;
import com.Practice.Render.Model.User;
import com.Practice.Render.Repositories.TicketRepository;
import com.Practice.Render.Repositories.UserRepository;
import com.Practice.Render.Service.CustomUserDetails;
import com.Practice.Render.Service.CustomUserDetailsService;
import com.Practice.Render.Service.Login;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This is an MVC application and therefore web controllers are used to trigger certain action when the web browser hits
 * a certain end point. For example http:localhost:8080/ or http:localhost:8080/index will display the web page index.html
 * in the web browser. It should be noted that these controllers will not be used if Spring Security 6 makes a reference
 * to a particular web page via the User Controller class
 */
@Controller
@Lazy //Solves the problem of too many redirects in the web browser
public class UserController
{
    /**
     * The field called "ticketRepository" is @Autowired in ordered to call methods from the TicketRepository class.
     * When @Autowired is done, "ticketRepository" does not need to be passed to the method as a parameter.
     */
    @Autowired
    private TicketRepository ticketRepository;

    /**
     * The field called "userRepository" is @Autowired in ordered to call methods from the UserRepository class.
     * When @Autowired is done, "userRepository" does not need to be passed to the method as a parameter.
     */
    @Autowired
    private UserRepository userRepository;

    private String encryptedPassword;

    /* Encode the raw password from the user, which comes from a form */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The field myLogin is used in ordered to call methods from the Login class. Since myLogin is NOT @Autowired
     * it must be passed as a parameter to a method
     */
    Login mylogin;

    /**
     * The field called "userDetailsService" is @Autowired in ordered to call methods from the userDetailsService class
     *  When @Autowired is done, "userDetailsService" does not need to be passed to the method as a parameter.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /* There is more than one form with a ticketid, so numbers after ticketid (ticketid6 for example) are used to distinguish between them  */
    int ticketid6;
    int ticketid7;
    String ticketstatus6;


    /**
     * When the dispatcher servlet matches the request to the URL "/error", the error() method will display the
     * error.html web page by returning the String error.html
     * @return error.html
     */
    @GetMapping("/user/error")
    public String error()
    {
        System.out.println("error");
        return "/user/error.html";
    }

    /**
     * When the dispatcher servlet matches the request to the URL "/", OR "/index", the showForm() method it will
     * display the index web page by returning the String index.html
     * @return index.html
     */
    @GetMapping("/user/index")
    public String showForm()
    {
        System.out.println("index: GetMapping");
        return "/user/index.html";
    }

    /**
     * When the dispatcher servlet matches the request to the URL "/index" for a POST request (the previous request
     * was for a GET request), postForm() will return posted data from an HTML form back to home.html
     * @return home.html
     */
    @PostMapping("/user/index")
    public String postForm()
    {
        System.out.println("Andre POST: /index");
        return "/user/home.html";
    }

    /**
     * When the dispatcher servlet matches the request to the URL "/home" after the user is authenticated and
     * authorized home( ) it will display home.html along with user information via the Fetch API and user ticket
     * information
     * @return home.html
     */
    @GetMapping("/user/home")
    public String home( )
    {
        System.out.println("/home");
        return "/user/home.html";
    }


    /**
     * There is also a function named loggInUser in the Login class in the Service package
     * the purpose of loggInUser in this UserController class is to call mylogin.loggInUser, so it can
     * be used as an endpoint so repaymentScript.js can catch and display the username of the current
     * user on the home.html, NOTE: @ResponseBody is used to return a String and not HTML
     * @param principal
     * @param mylogin
     * @return String
     * @throws JsonProcessingException
     */
    @GetMapping("/user/loggInUser")
    @ResponseBody
    public String loggInUser(Principal principal, Login mylogin) throws JsonProcessingException
    {
        this.mylogin = mylogin;
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        System.out.println("Current User in UserController.java: " + customUserDetails.toString());
        String theCurrentUser =  mylogin.loggInUser(principal, customUserDetailsService);
        return theCurrentUser;

    }

    /** /admin/loggInUser will display which Manager or Admin is logged in. This information is caught by the
    Fetch API using managementScript.js
    * @param principal
    * @param mylogin
    * @return String
    * @throws JsonProcessingException
    */
    @GetMapping("/admin/loggInUser")
    @ResponseBody
    public String loggInAdmin(Principal principal, Login mylogin) throws JsonProcessingException
    {
        this.mylogin = mylogin;
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        System.out.println("Current User in UserController.java: " + customUserDetails.toString());
        String theCurrentUser =  mylogin.loggInUser(principal, customUserDetailsService);
        return theCurrentUser;

    }

    /**
     * When the dispatcher servlet matches the request to the URL "/logout", logoutUser() will display logout.html
     * and then redirect to index.html
     * @return logout.html
     */
    @PostMapping("/user/logout")
    public String logoutUser()
    {
        System.out.println(" ((())) /user/logout");
        return "/user/logout.html";
    }
    /**
     * When the dispatcher servlet matches the request to the URL "/admin/logout", logoutAdmin() will display managerLogout.html
     * and then redirect to index.html
     * @return logout.html
     */

    @PostMapping("/admin/logout")
    public String logoutAdmin()
    {
        System.out.println("outter here /admin/logout");
        return "/admin/managerLogout.html";
    }

    /**
     *  When the dispatcher servlet matches the request to the URL "/currentUserInfo", currentUserInfo(Principal principal, Login mylogin)
     *  will grab references to myLogin and userdetails to retrieve user information, parse the output String so that it is properly
     *  formated and it will return the String outputString1 which will be caught by the Fetch API to be displayed on home.htm
     * @param principal
     * @param mylogin
     * @return String
     * @throws JsonProcessingException
     */
    @GetMapping("/user/currentUserInfo")
    @ResponseBody
    public String currentUserInfo(Principal principal, Login mylogin) throws JsonProcessingException
    {
        this.mylogin = mylogin;
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        String CurrentUserInformation = mylogin.currentUserInfo(principal, customUserDetailsService);
        String[] outputString1 = CurrentUserInformation.split(",");


        return outputString1[0] + ",   " + outputString1[1] +  ",   " + outputString1[2] + ",   "
                + outputString1[3] +  ",   " +outputString1[4] +  ",   " +outputString1[5] + ",   "
                + outputString1[6] + ",   " + outputString1[7] + ",   " + outputString1[8] + ",   "
                + outputString1[10] + ",   " + outputString1[11];
    }

    /**
     *  When the dispatcher servlet matches the request to the URL "/admin/currentUserInfo", currentAdminInfo(Principal principal, Login mylogin)
     *  will grab references to myLogin and customUserDetails to retrieve user information, parse the output String so that it is properly
     *  formated and it will return the String outputString1 which will be caught by the Fetch API to be displayed on managerHome.html
     * @param principal
     * @param mylogin
     * @return String
     * @throws JsonProcessingException
     */
    @GetMapping("/admin/currentUserInfo")
    @ResponseBody
    public String currentAdminInfo(Principal principal, Login mylogin) throws JsonProcessingException
    {
        this.mylogin = mylogin;
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        String CurrentUserInformation = mylogin.currentUserInfo(principal, customUserDetailsService);
        String[] outputString1 = CurrentUserInformation.split(",");


        return outputString1[0] + ",   " + outputString1[1] +  ",   " + outputString1[2] + ",   "
                + outputString1[3] +  ",   " +outputString1[4] +  ",   " +outputString1[5] + ",   "
                + outputString1[6] + ",   " + outputString1[7] + ",   " + outputString1[8] + ",   "
                + outputString1[10] + ",   " + outputString1[11];
    }

    /**
     * When the dispatcher servlet matches the request to the URL "/displayMyTickets", displayMyTickets(Login mylogin, Principal principal)
     * will grab references to myLogin and userdetails. It will then parse the String information retrieved from userDetailsService
     * via "split", "regex", "Pattern.compile", "pattern.matcher" and "parseInt(result)". The ultimate purpose of all parsing is to get
     * the employeeid in String form and then convert it into an int. Lastly it will call mylogin.AllMyTickets(id, ticketRepository)
     * where id = employeeid and ticketRepository is a reference to the repository that has access to the tickets in the database.
     * Ultimately it will display all tickets for a particular user/employee
     * @param mylogin
     * @param principal
     * @return List<String>
     */
    @GetMapping("/user/displayMyTickets")
    @ResponseBody
    public List<Tickets> displayMyTickets(Login mylogin, Principal principal)
    {

        this.mylogin = mylogin;
        String CurrentUserInformation = mylogin.currentUserInfo(principal, customUserDetailsService);
        String[] outputString1 = CurrentUserInformation.split(",");


        String stringID = outputString1[0]; // returns employeeid:1, only need numeric
        String str = stringID;
        String regex = "[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        String result = pattern.matcher(str).replaceAll("");

        int id=Integer.parseInt(result);
        return mylogin.AllMyTickets(id, ticketRepository);
    }

    /**
     * The purpose of this method is to add new tickets for a particular user/employee. The parameters come from a form post. The
     * employeeid is converted to a number, then  ticketRepository.addNewTicket() with parameters is called. Finally, it will
     * redirect to home.html where the new ticket will be displayed for that particular user/employee
     * @param principal
     * @param description1
     * @param ticketstatus1
     * @param amount1
     * @param reimbursement_type1
     * @return redirect:home.html
     */
    @PostMapping("/user/addTicket")
    public String addTicket(Principal principal, @RequestParam("description1") String description1, @RequestParam("ticketstatus1") String ticketstatus1,
                            @RequestParam("amount1") float amount1, @RequestParam("reimbursement_type1") String reimbursement_type1)
    {
        Timestamp datesubmitted = new Timestamp(System.currentTimeMillis());
        Timestamp dateresolved = null;
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(principal.getName());

        //Chop off the first few characters of  the string to get the string representation of employeeid and then convert it into an int
        String[] arrOfEmployeeIDString =  userDetails.toString().split(",", 2);
        String subEmployeeID = arrOfEmployeeIDString[0];
        String tinypice = subEmployeeID.substring(11);
        int employeeid=Integer.parseInt(tinypice);

        ticketRepository.addNewTicket(description1, ticketstatus1, amount1, datesubmitted, dateresolved, reimbursement_type1, employeeid);

        return "redirect:/user/home.html";
    }

    /**
     * The purpose of this method is to edit an existing ticket for a particular user/employee. The parameters come from
     * a form post. The underlying SQL is based on ticketid (not employeeid) Finally, it will redirect to home.html
     * where the existing ticket will be displayed with the new information for that particular user/employee
     * @param ticketid2
     * @param description2
     * @param ticketstatus2
     * @param amount2
     * @param reimbursement_type2
     * @return redirect:home.html
     */
    @PostMapping("/user/editTicket")
    public String editTicket(@Param("ticketid2") int ticketid2, @Param("description2") String description2, @Param("ticketstatus2") String ticketstatus2,
                             @Param("amount2") float amount2, @Param("reimbursement_type2") String reimbursement_type2)
    {
        ticketRepository.editTicket(ticketid2, description2, ticketstatus2, amount2, reimbursement_type2);
        return "redirect:/user/home.html";
    }

    /**
     * The purpose of this method is to delete an existing ticket for a particular user/employee. Only the ticketid is needed
     * to delete the ticket from the database. The parameters come from a form post. Finally, it will redirect to home.html
     * where the existing ticket will be displayed with the new information for that particular user/employee
     * @param ticketid3
     * @return
     */
    @PostMapping("/user/deleteTicket")
    public String deleteTicket(@Param("ticketid3") int ticketid3)
    {
        ticketRepository.deleteTicket(ticketid3);
        return "redirect:/user/home.html";
    }

//Manager Controllers Below

    /**
     * The purpose of this method is to display the manager error page
     * @return /admin/managerError.html
     */
    @GetMapping("/admin/managerError")
    public String ManagerError( )
    {
        System.out.println("GetMapping: /managerError");
        return "/admin/managerError.html";
    }

    /**
     * The purpose of this method is to display the login page for managers
     * @return /admin/managerLogin.html
     */
    @GetMapping("/admin/managerLogin")
    public String mrManager()
    {
        System.out.println("GET Mapping: /MrManager ***");
        return "/admin/managerLogin.html";
    }

    /**
     * The purpose of this method is to send the form data from /admin/managerLogin.html to /admin/managerHome.html"
     * @param user
     * @return /admin/managerHome.html"
     */
    @PostMapping("/admin/managerLogin")
    public String PostManager(@ModelAttribute User user )
    {
        System.out.println("POST Mapping: /PostManager ***");
        return "/admin/managerHome.html";
    }

    /**
     * The purpose of this method is for the admin to either approve or deny the cost of reimbursement to employees who
     * spent money out of their own pocket for the benefit of the company. Also the manager/admin can delete old tickets
     * for employees who are no longer with the company
     * @return /admin/managerHome.html.
     */
    @GetMapping("/admin/managerHome")
    public String ManagerHome( )
    {
        System.out.println("GetMapping: /managerHome");
        return "/admin/managerHome.html";
    }

    /**
     * The purpose of this method is to catch the form data submitted from a form on /admin/managerHome.html
     * @return /admin/managerHome.html
     */
    @PostMapping("/admin/managerHome")
    public String PostManagerHome( )
    {
        System.out.println("PostMapping: /managerHome");
        return "/admin/managerHome.html";
    }


    /**
     * The purpose of this method is to display the logout page for managers
     * @return /admin/managerLogout.html
     */
    @PostMapping("/admin/managerLogout")
    public String ManagerLogout()
    {
        System.out.println("PostMapping: /managerLogout");
        return "/admin/managerLogout.html";
    }

    /**
     * The purpose of this method is to show the manager all tickets from all employees in order for the manager to
     * approve deny or delete tickets
     * @param mylogin
     * @param principal
     * @return List<String> , which is caught by the Fetch API via managementScript.js
     */
    @GetMapping("/admin/displayAllTickets")
    @ResponseBody
    public List<Tickets> displayAllTickets(Login mylogin, Principal principal)
    {
        this.mylogin = mylogin;
        return mylogin.allTickets(ticketRepository);
    }

    /**
     * The purpose of this method is to approve or deny reimbursements by submitting the required form data for processing
     * @param mylogin
     * @param ticketid6
     * @param ticketstatus6
     * @return redirect:/admin/managerHome.html
     */
    @PostMapping("/admin/approveDenyATicket")
    public String approveDenyATicket(Login mylogin, @Param("ticketid6") int ticketid6, @Param("ticketstatus6") String ticketstatus6)
    {
        this.mylogin = mylogin;
        this.ticketid6 = ticketid6;
        this.ticketstatus6 = ticketstatus6;
        mylogin.decision(ticketid6, ticketstatus6, ticketRepository);
        return "redirect:/admin/managerHome.html";
    }

    /**
     * The purpose of this method is to delete an employee ticket by submitting the required form data for processing
     * @param mylogin
     * @param ticketid7
     * @return redirect:/admin/managerHome.html
     */
    @PostMapping("/admin/deleteATicket")
    public String deleteATicket(Login mylogin, @Param("ticketid7") int ticketid7)
    {
        this.mylogin = mylogin;
        this.ticketid7 = ticketid7;
        mylogin.disapearingTicket(ticketid7, ticketRepository);
        return "redirect:/admin/managerHome.html";
    }


    /**
     * The purpose of this method is to  display all employees who have the ability to be reimbursed for cost out of their
     * own pocket that benefited the company
     * @param mylogin
     * @return List<String> , caught by the Fetch API via devineCreation.js
     */
    @GetMapping("/admin/createDeleteEmployees")
    @ResponseBody
    public List<String> onoffBoarding(Login mylogin)
    {
        this.mylogin = mylogin;
        return mylogin.allEmployees(userRepository);

    }

    /**
     * The purpose of this method is for the admin or manager to create a user/employee who can be reimbursed for expenses.
     * Also, userRepository.addAuthority is called right after  userRepository.addEmployee so Spring Security can
     * authenticate the new user/employee
     * @param role
     * @param enabled
     * @param username
     * @param jobtitle
     * @param firstname
     * @param lastname
     * @param phone
     * @param email
     * @param password
     * @return redirect:/admin/manageEmployees.html
     */
    @PostMapping("/admin/addEmployee")
    public String addEmployee(@RequestParam("role") String role, @RequestParam("enabled") Boolean enabled, @RequestParam("username") String username,
                            @RequestParam("jobtitle") String jobtitle, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
                            @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("password") String password)
    {
        encryptedPassword =  passwordEncoder.encode(password);

        Timestamp  datehired = new Timestamp(System.currentTimeMillis());
        Timestamp  datefired = null;
        userRepository.addEmployee(role, enabled, username, jobtitle, firstname, lastname, phone, email, encryptedPassword, datehired, datefired);
        userRepository.addAuthority(username, role);
        return "redirect:/admin/manageEmployees.html";
    }


    /**
     * The purpose of this method is to delete an employee/user who is no longer with the company
     * @param employeeid
     * @return redirect:/admin/manageEmployees.html
     */
    @PostMapping("admin/deleteEmployee")
    public String deleteEmployee(@RequestParam("employeeid") int employeeid)
    {
        userRepository.deleteOneUser(employeeid);
        return "redirect:/admin/manageEmployees.html";
    }

    @GetMapping("/error/manager-access-denied")
    public String managerAccessDenied()
    {
        return "/user/managerErrorPage.html"; // The name of the error page view
    }

    @GetMapping("/error/access-denied")
    public String accessDenied()
    {
        return "/user/accessDeniedPage.html"; // The name of the generic error page view
    }




}
