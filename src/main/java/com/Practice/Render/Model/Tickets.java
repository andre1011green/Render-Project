package com.Practice.Render.Model;

import jakarta.persistence.*;

/**
 * In this application tickets are owned by users/employees. Note: one user/employee can have many tickets.
 * The purpose of a ticket is to keep a record for reimbursement request
 * of employees who spent their own money on company business.
 */

@Entity
@Table(name = "tickets")
public class Tickets
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketid;

    private String description;

    private String ticketstatus;

    private float amount;

    private String datesubmitted;

    private String dateresolved;

    private String reimbursement_type;

    private int employeeid;

    public Tickets() { }

    public Tickets(int ticketid, String description, String ticketstatus, float amount, String datesubmitted, String dateresolved, String reimbursement_type, int employeeid)
    {
        this.ticketid = ticketid;
        this.description = description;
        this.ticketstatus = ticketstatus;
        this.amount = amount;
        this.datesubmitted = datesubmitted;
        this.dateresolved = dateresolved;
        this.reimbursement_type = reimbursement_type;
        this.employeeid = employeeid;
    }

    public int getTicketID()
    {
        return ticketid;
    }


    public void setTicketID(int ticketid)
    {
        this.ticketid = ticketid;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTicketStatus()
    {
        return ticketstatus;
    }

    public void setTicketStatus(String ticketstatus)
    {
        this.ticketstatus = ticketstatus;
    }

    public float getAmount()
    {
        return amount;
    }

    public String getDateSubmitted()
    {
        return datesubmitted;
    }

    public void setDateSubmitted(String datesubmitted)
    {
        this.datesubmitted = datesubmitted;
    }

    public String getDateResolved()
    {
        return dateresolved;
    }

    public void setDateResolved(String dateresolved)
    {
        this.dateresolved = dateresolved;
    }

    public String getReimbursementType()
    {
        return reimbursement_type;
    }

    public void setReimbursementType(String reimbursement_type)
    {
        this.reimbursement_type = reimbursement_type;
    }

    public int getEmployeeID()
    {
        return employeeid;
    }

    public void setEmployeeID(int employeeid)
    {
        this.employeeid = employeeid;
    }

    @Override
    public String toString()
    {
        return "{ticketid:" + ticketid + " description:" + description + " ticketstatus:" + ticketstatus +
                " amount:" + amount + " dateSubmitted:" + datesubmitted + " dateresolved:" + dateresolved +
                " reimbursement_type:" + reimbursement_type + " employeeid:" + employeeid  + "}";
    }


}


