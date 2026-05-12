package com.Practice.Render.Service;

import com.Practice.Render.Model.User;
import com.Practice.Render.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The purpose of this class CustomUserDetailsService is to provide database methods to load CustomUserDetails
 */
@Service
public class CustomUserDetailsService implements UserDetailsService
{
    User someUser;
    List<String> manyUsers;

    /**
     * The field userRepository is @Autowired, so it does not need to be passed as a parameter. It is also a reference
     * to use repository methods
     */

    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * The purpose of this method loadUserByUsername(String username) is to find a user in the Database. It returns a
     * UserDetails which is actually a User. If UsernameNotFoundException is thrown then this application will redirect to
     * error.html where it will display the message "Bad Credentials" and then redirect to index.html
     *
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */



    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        someUser = userRepository.findByUsername(username);
        if (someUser == null)
        {
            System.out.println("From CustomUserDetailsService: Username or Password not found!");
            throw new UsernameNotFoundException("Username or Password not found!");
        }

        return new CustomUserDetails(someUser.getEmployeeid(), someUser.getAuthorities(), someUser.isEnabled(), someUser.getUsername(), someUser.getJobtitle(),
                someUser.getFirstname(), someUser.getLastname(), someUser.getPhone(), someUser.getEmail(), someUser.getPassword(),
                someUser.getDatehired(), someUser.getDatefired());
    }




}
