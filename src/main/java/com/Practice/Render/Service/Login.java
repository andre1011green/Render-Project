package com.Practice.Render.Service;

import com.Practice.Render.Model.Tickets;
import com.Practice.Render.Repositories.TicketRepository;
import com.Practice.Render.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

/**
 * The purpose of this class Login is to get user  login information and ticket information. These methods are called
 * inside a controller
 */
@Service
public class Login
{
    /**
     * This field is needed to call ticketRepository methods
     */
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * The purpose of this method loggInUser(Principal principal, UserDetailsService userDetailsService) is to display
     * the username of the logged-in user. It will return a String which will be caught by the Fetch API
     * @param principal
     * @param customUserDetailsService
     * @return String
     */
    public String loggInUser(Principal principal, CustomUserDetailsService customUserDetailsService)
    {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(principal.getName());

        if (userDetails != null)
        {
            System.out.println("Andre the current user is " + userDetails.getUsername());
            return userDetails.getUsername();
        }
        else
        {
            System.out.println("No current user");
            return "No Current User";
        }

    }

    /**
     * The purpose of this method currentUserInfo(Principal principal, UserDetailsService userDetailsService) is to display
     * all user information on the home.html web page. It will return a String which will be caught by the Fetch API
     *
     * @param principal
     * @param customUserDetailsService
     * @return
     */
    public String currentUserInfo(Principal principal, CustomUserDetailsService customUserDetailsService)
    {
        CustomUserDetails  andre = customUserDetailsService.loadUserByUsername(principal.getName());
        if (andre  != null)
        {
            BigInteger intID = andre.getEmployeeid(); //  This is a number
            return andre.toString();
        }
        else
        {
            return "No Current User";
        }
    }

    /**
     * The purpose of this method AllMyTickets(int id, TicketRepository ticketRepository) is to display all tickets of a
     * particular user. It is called inside a controller. It returns a list of Strings which is caught by the
     * Fetch API
     * @param id
     * @param ticketRepository
     * @return List<String>
     */
    public List<Tickets> AllMyTickets(int id, TicketRepository ticketRepository)
    {
        this.ticketRepository = ticketRepository;
        List<Tickets> myTickets = ticketRepository.findAllByEmployeeID(id);
        if(myTickets.isEmpty())
        {
            System.out.println("Did not find any Tickets for given empoyeeid");
        }
        //System.out.println("My Tickets: " + myTickets.toString());
        return myTickets;
    }

    /**
     * The purpose of this method is to display a list of all tickets to the admin
     * @param ticketRepository
     * @return List<String>, caught by the Fetch API via managementScript.js
     */
    public List<Tickets> allTickets(TicketRepository ticketRepository)
    {
        this.ticketRepository = ticketRepository;
        List<Tickets> allTickets = ticketRepository.findAllTickets();
        if(allTickets.isEmpty())
        {
            System.out.println("Did not find any Tickets for any employees");
        }
        //System.out.println("All Tickets: " + allTickets.toString());
        return allTickets;
    }

    /**
     * The purpose of this method is to allow the admin to edit a ticket and decide if a reimbursement ticket is approved or denied
     * @param ticketid6
     * @param ticketstatus6
     * @param ticketRepository
     */
    public void decision(int ticketid6, String ticketstatus6, TicketRepository ticketRepository)
    {
        this.ticketRepository = ticketRepository;
        ticketRepository.approveDenyOneTicket(ticketid6, ticketstatus6);
    }

    /**
     * The purpose of this method is to allow an admin to delete a ticket
     * @param ticketid7
     * @param ticketRepository
     */
    public void disapearingTicket(int ticketid7, TicketRepository ticketRepository)
    {
        this.ticketRepository = ticketRepository;
        //System.out.println("Bye Bye Data, ticketid7 = " + ticketid7);
        ticketRepository.deleteOneTicket(ticketid7);
    }

    /**
     *
     * @param userRepository
     * @return List<String> , caught by the Fetch API via devineCreation.js
     */
    public List<String> allEmployees(UserRepository userRepository)
    {
        this.userRepository = userRepository;
        List<String> allEmployees = userRepository.findAllCompanyEmployees();
        if(allEmployees.isEmpty())
        {
            System.out.println("Did not find any any employees");
            System.exit(0);
        }
        return allEmployees;
    }


}
