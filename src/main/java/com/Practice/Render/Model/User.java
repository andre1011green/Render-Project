package com.Practice.Render.Model;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;


/**
 * The user in this application is an employee. The User class is used and not Employee class because of implementing custom
 * tables with Spring Security (using the name 'employees' did not work). This class stores all the corresponding information
 * located in the Postgres Database. Note: The users table and the tickets table are linked together by employeeid.
 */
@Entity
@Table(name="users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger employeeid;

    @Column(name="role")
    private String role;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="username")
    private String username;

    @Column(name="jobtitle")
    private String jobtitle;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="datehired")
    private Timestamp datehired;

    @Column(name="datefired")
    private Timestamp datefired;

    public User() { }

    public User(String role, boolean enabled, String username, String jobtitle, String firstname,
                String lastname, String phone, String email, String password, Timestamp datehired,
                Timestamp datefired)
    {
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

    public BigInteger getEmployeeid() {return employeeid;}

    public void setEmployeeid(BigInteger employeeid) {this.employeeid = employeeid;}

    public String getAuthorities() {return getRole();}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    public boolean isEnabled() {return enabled;}

    public void setEnabled(boolean enabled) {this.enabled = enabled;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getJobtitle() {return jobtitle;}

    public void setJobtitle(String jobtitle) {this.jobtitle = jobtitle;}

    public String getFirstname() {return firstname;}

    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getLastname() {return lastname;}

    public void setLastname(String lastname) {this.lastname = lastname;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Timestamp getDatehired() {return datehired;}

    public void setDatehired(Timestamp datehired) {this.datehired = datehired;}

    public Timestamp getDatefired() {return datefired;}

    public void setDatefired(Timestamp datefired) {this.datefired = datefired;}

    @Override
    public String toString()
    {
        return "User{" +
                "employeeid=" + employeeid +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", username='" + username + '\'' +
                ", jobtitle='" + jobtitle + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", datehired=" + datehired +
                ", datefired=" + datefired +
                '}';
    }
}
