/*
   Filename: managementScript.js
   Author:   Andre Long
   Date:     10/11/2024
   Description: The purpose of this Javascript file is to display all tickets
   for all employees
*/

window.onload = start;

function start() {
    ether();
}

function ether() {

    const welcomeMrManager = document.getElementById('welcomeManager');
    const managerInfoDiv = document.getElementById('managerInfo');
    const allEmployeesDiv = document.getElementById('allEmployees');

    const BASE_URL = "https://render-project-szn7.onrender.com";

    fetch(`${BASE_URL}/admin/loggInUser`)
        .then(response => response.text())
        .then(text => {
            welcomeMrManager.innerHTML = "<h2 id='welcomeManagerDiv'>Welcome  " + text  +
                "<form id='adminlogout' method='post' action='/admin/managerLogout'>" +
                "<input type='submit' value='Sign Out'></form></h2>";
        });

    fetch(`${BASE_URL}/admin/currentUserInfo`)
        .then(response => response.text())
        .then(text => {
            managerInfoDiv.innerHTML = "<h4 id='managerInfoMessage'>  " + text + "</h4>";
        });

    fetch(`${BASE_URL}/admin/displayAllTickets`)
        .then(response => response.json())
        .then(tickets => {
            let info = "";
            tickets.forEach(ticket => {
                info += `
                    <tr class='suckerFish'>
                        <td class='sonOfSuckerFish'>${ticket.ticketID}</td>
                        <td class='sonOfSuckerFish'>${ticket.description}</td>
                        <td class='sonOfSuckerFish'>${ticket.ticketStatus}</td>
                        <td class='sonOfSuckerFish'>${ticket.amount}</td>
                        <td class='sonOfSuckerFish'>${ticket.dateSubmitted}</td>
                        <td class='sonOfSuckerFish'>${ticket.dateResolved}</td>
                        <td class='sonOfSuckerFish'>${ticket.reimbursementType}</td>
                        <td class='sonOfSuckerFish'>${ticket.employeeID}</td>
                    </tr>
                `;
            });

            let Outer = `
                <table id='AndreTable'>
                    <tr>
                        <th id='ticketid'>Ticket Number</th>
                        <th id='description'>Ticket Description</th>
                        <th id='ticketstatus'>Ticket Status</th>
                        <th id='amount'>Reimbursement Amount</th>
                        <th id='datesubmitted'>Date Submitted</th>
                        <th id='dateresolved'>Date Resolved</th>
                        <th id='reimbursementtype'>Reimbursement Type</th>
                        <th id='employeeid'>Employee ID</th>
                    </tr>
            `;

            allEmployeesDiv.innerHTML = Outer + info + "</table>";
        });
}
