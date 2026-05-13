package com.Practice.Render.Repositories;

import com.Practice.Render.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * The purpose of this class is to interact with the PostGres Database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    /**
     * The purpose of this method is to find users/employees in the Database based on username. It will return a User.
     * It is called by CustomUserDetailsService
     * @return User
     */
    User findByUsername(String username);

    /**
     * The purpose of this method is to find all employees who are in the Postgres database so they can be displayed
     * @return List<String> , caught by the Fetch API via devineCreation.js
     */
    @Query(nativeQuery = true,  value = "SELECT * FROM users")
    List<Object[]> findAllCompanyEmployees();

    /**
     * The purpose of this method is to add employees to the Postgres database
     * @param role
     * @param enabled
     * @param username
     * @param jobtitle
     * @param firstname
     * @param lastname
     * @param phone
     * @param email
     * @param password
     * @param datehired
     * @param datefired
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true,  value = "INSERT INTO users(role,enabled, username,jobtitle, firstname, lastname, phone, email,password," +
            "datehired, datefired) values(:role, :enabled, :username, :jobtitle, :firstname, :lastname, :phone, :email, :password, :datehired, :datefired)")
    void addEmployee(@Param("role") String role, @Param("enabled") Boolean enabled, @Param("username") String username,
                     @Param("jobtitle") String jobtitle, @Param("firstname") String firstname, @Param("lastname") String lastname,
                     @Param("phone") String phone, @Param("email") String email, @Param("password") String password,
                     @Param("datehired")Timestamp datehired, @Param("datefired") Timestamp datefired);

    /**
     * The purpose of this method is to insert a user's authority/role into the Postgres database so Spring Security can authenticate the user
     * @param username
     * @param authority
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true,  value = "INSERT INTO authorities (username, authority) VALUES (:username, :authority)")
    void addAuthority(@Param("username") String username, @Param("authority") String authority);


    /**
     * The purpose of this method is to delete a user/employee. The Postgres database is set up in a way so that when a
     * user is deleted the user's tickets are also deleted
     * @param employeeid
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true,  value = "DELETE FROM users WHERE employeeid = :employeeid;")
    void deleteOneUser(@Param("employeeid") int employeeid);

}
