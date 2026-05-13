/*
   Filename: devineCreation.js
   Author:   Andre Long
   Date:     10/17/2024
   Description: The purpose of this Javascript file is to display all employees
   and to either create or delete employees
*/

window.onload = begin;

function begin()
{
    const peppermintDiv = document.getElementById('companyEmployees');

    const BASE_URL = "https://render-project-szn7.onrender.com";

    fetch(`${BASE_URL}/admin/createDeleteEmployees`)
        .then(response => response.text())
        .then(text => {

            let info = " ";
            let snoopdog = JSON.parse(text);

            for (let j = 0; j < snoopdog.length; j++) {

                let data = snoopdog[j];
                let oneEmployeeArray = data.split(",");

                info += `
                <tr class='suckerFish'>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[0]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[1]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[2]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[3]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[4]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[5]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[6]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[7]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[8]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[10]}</td>
                    <td class='sonOfSuckerFish'>${oneEmployeeArray[11]}</td>
                </tr>
            `;
            }

            let Outer = `
            <table id='AndreTable'>
                <tr>
                    <th id='employeeid'>Employee ID</th>
                    <th id='role'>Role</th>
                    <th id='enabled'>Active</th>
                    <th id='username'>Username</th>
                    <th id='jobtitle'>Job Title</th>
                    <th id='firstname'>Firstname</th>
                    <th id='lastname'>Lastname</th>
                    <th id='phone'>Phone #</th>
                    <th id='email'>Email</th>
                    <th id='datehired'>Date Hired</th>
                    <th id='datefired'>Date Fired</th>
                </tr>
        `;

            peppermintDiv.innerHTML = Outer + info + "</table>";
        });
}
