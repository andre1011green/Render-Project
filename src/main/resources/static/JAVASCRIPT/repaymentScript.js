/*
   Filename: repaymentScript.js
   Author:   Andre Long
   Date:     2/20/2024
   Description: The purpose of this Javascript file is to display all tickets
   for a single employee
*/

window.onload = start();

function start()
{
	ether();
}

function ether() {
    const welcomeDiv = document.getElementById('welcome');
    const userInfoDiv = document.getElementById('userInfo');
    const reimbursementDiv = document.getElementById('MyReimbursements');

    const BASE_URL = "https://render-project-szn7.onrender.com";

    fetch(`${BASE_URL}/user/loggInUser`)
        .then(response => response.text())
        .then(text => {
            welcomeDiv.innerHTML = "<h2 id='welcomeMessage'>Welcome  " + text + "</h2>" +
                "<form id='andrelogout' method='post' action='/user/logout'>" +
                "<input type='submit' value='Sign Out'>" +
                "</form>";
        });

    fetch(`${BASE_URL}/user/currentUserInfo`)
        .then(response => response.text())
        .then(text => {
            userInfoDiv.innerHTML = "<h4 id='userInfoMessage'>  " + text + "</h4>";
        });

    fetch(`${BASE_URL}/user/displayMyTickets`)
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

            reimbursementDiv.innerHTML = Outer + info + "</table>";
        });
}








