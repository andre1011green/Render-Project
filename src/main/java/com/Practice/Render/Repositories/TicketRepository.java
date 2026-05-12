package com.Practice.Render.Repositories;

import com.Practice.Render.Model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;


/**
 * The purpose of TicketRepository is to provide CRUD methods to interact with the Postgres Database
 */
public interface TicketRepository extends JpaRepository<Tickets, Integer>
{
    /**
     * This method will return all employee tickets based on employeeid. It uses a Native Query (regular SQL) and returns
     * a list of Strings which will be caught bt the Fetch API and displayed on home.html
     * @param employeeid
     * @return List<String>
     */
    @Query(nativeQuery = true,  value = "SELECT * FROM tickets  WHERE employeeid = :employeeid")
    List<Tickets> findAllByEmployeeID(@Param("employeeid") int employeeid);

    /**
     * This method will add a new ticket to the Database. It uses  @Transactional and @Modifying because it will modify
     * the Database. It will return void.
     * @param description
     * @param ticketstatus
     * @param amount
     * @param datesubmitted
     * @param dateresolved
     * @param reimbursement_type
     * @param employeeid
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="insert into tickets (description, ticketstatus, amount,  datesubmitted, dateresolved, reimbursement_type, employeeid ) " +
            "values (:description, :ticketstatus, :amount, :datesubmitted, :dateresolved, :reimbursement_type, :employeeid)")
    void addNewTicket(@Param("description") String description, @Param("ticketstatus") String ticketstatus,
                         @Param("amount") float amount, @Param("datesubmitted") Timestamp datesubmitted, @Param("dateresolved") Timestamp dateresolved,
                         @Param("reimbursement_type") String reimbursement_type, @Param("employeeid") int employeeid);


    /**
     * This method will modify an existing ticket in the Database based on the ticketid. It uses  @Transactional and @Modifying because it will modify
     * the Database. It will return void.
     * @param ticketid2
     * @param description2
     * @param ticketstatus2
     * @param amount2
     * @param reimbursement_type2
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="UPDATE tickets SET ticketid = :ticketid2, description = :description2, ticketstatus =:ticketstatus2," +
            "amount =:amount2, reimbursement_type =:reimbursement_type2  Where ticketid = :ticketid2" )
    void editTicket(@Param("ticketid2") int ticketid2, @Param("description2") String description2, @Param("ticketstatus2") String ticketstatus2,
                    @Param("amount2") float amount2, @Param("reimbursement_type2") String reimbursement_type2);


    /**
     * This method will delete an existing ticket in the Database based on the ticketid. It uses  @Transactional and @Modifying
     * because it will modify the Database. It will return void.
     * @param ticketid3
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tickets WHERE ticketid =:ticketid3;")
    void deleteTicket(@Param("ticketid3") int ticketid3);

    /**
     * The purpose of this method is to display all tickets submitted by employees
     * @return List<String> , caught by the Fetch API via managementScript.js
     */
    @Query(nativeQuery = true,  value = "SELECT * FROM tickets")
    List<Tickets> findAllTickets();


    /**
     * The purpose of this method is to edit a ticket so a manager can approve or deny reimbursement
     * @param ticketid6
     * @param ticketstatus6
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE tickets SET ticketstatus = :ticketstatus6, dateresolved = current_timestamp Where ticketid = :ticketid6")
    void approveDenyOneTicket(@Param("ticketid6") int ticketid6, @Param("ticketstatus6") String ticketstatus6);


    /**
     * The purpose of this method is to delete a ticket, different forms use ticketid so numbered tickets are used for example ticketid7
     * @param ticketid7
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tickets WHERE ticketid = :ticketid7")
    void deleteOneTicket(@Param("ticketid7") int ticketid7);
}
