package com.Practice.Render.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The purpose of this class CustomUserDetails is to represent user information which CustomUserDetailsService will
 * load via database methods. CustomUserDetails is being used instead of userDetails because this application
 * needs access to all fields / relevant to all information for a user. The standard userDetails class that comes with
 * Spring Security 6 will only access username, password and role/authorities.
 * NOTE: Discovered: That for custom tables with Spring Security is that you can have unique table names and columns
 * but can have only 3 parameters for jdbcUserDetailsManager.setUsersByUsernameQuery and only 2 parameters for
 * jdbcUserDetailsManager.setAuthoritiesByUsernameQuery (Now switch to JPA and a custom userDetails which is a user)
 */
public class CustomUserDetails implements UserDetails // This is a user object
{
    private final BigInteger employeeid;
    private String role;
    private final boolean enabled;
    private final String username;
    private final String jobtitle;
    private final String firstname;
    private final String lastname;
    private final String phone;
    private final String email;
    private final String password;
    private final Timestamp datehired;
    private final Timestamp datefired;

    public CustomUserDetails(BigInteger employeeid, String role, boolean enabled, String username, String jobtitle,
                             String firstname, String lastname, String phone, String email, String password,
                             Timestamp datehired, Timestamp datefired)
    {
        this.employeeid = employeeid;
        this.role = role;
        this.enabled = enabled;
        this.username = username;
        this.jobtitle = jobtitle;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.datehired = datehired;
        this.datefired = datefired;

    }

    public BigInteger getEmployeeid(){return employeeid;}

    public String getRole() {return role;}

    public void setRole(String role){this.role = role;}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(getRole()));
        return list;
    }

    @Override
    public String getUsername() {return username;}

    public String getJobtitle() {return jobtitle;}

    public String getFirstname() {return firstname;}

    public String getLastname() {return lastname;}

    public String getPhone() {return phone;}

    public String getEmail() {return email;}

    @Override
    public String getPassword() {return password;}

    public Timestamp getDatehired() {return datehired;}

    public Timestamp getDatefired() {return datefired;}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}

    @Override
    public String toString()
    {
        return "employeeid:" + employeeid + "," +
                "role:" + role + "," +
                "enabled:" + enabled + "," +
                "username:" + username + "," +
                "jobtitle:" + jobtitle + "," +
                "firstname:" + firstname + "," +
                "lastname:" + lastname + "," +
                "phone:" + phone + "," +
                "email:" + email + "," +
                "password:" + password + "," +
                "datehired:" + datehired + "," +
                "datefired:" + datefired ;
    }
}
