-- filename: init.sql
--description: this file is used to create tables inside the Docker Container


\c ers_database

GRANT ALL PRIVILEGES ON DATABASE ers_database TO postgres;

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    employeeid                          SERIAL NOT NULL,
    role                                VARCHAR(255) NOT NULL,
    enabled                             boolean NOT NULL,
    username                            VARCHAR(255) NOT NULL,
    jobtitle                            VARCHAR(255) NOT NULL,
    firstname                           VARCHAR(255) NOT NULL,
    lastname                            VARCHAR(255) NOT NULL,
    phone                               VARCHAR(255) NOT NULL,
    email                               VARCHAR(255) NOT NULL,
    password                            VARCHAR(60)  NOT NULL,
    datehired                           timestamp    NOT NULL,
    datefired                           timestamp,
    CONSTRAINT                          employees_pkey PRIMARY KEY (employeeid),
    CONSTRAINT                          employees_email_key UNIQUE (email),
    CONSTRAINT                          employees_username_key UNIQUE (username)
);

INSERT INTO users (role, enabled, username, jobtitle, firstname, lastname, phone, email, password, datehired, datefired)
VALUES ('ROLE_EMPLOYEE', 'true', 'Fatfreddy', 'CEO', 'Fred', 'Flinstone', '555-123-4567', 'flinstoneF@Bedrockquarry.com',
'$2a$12$Lg6dcrZu22XCOIWVgQkDFu6U8vrJwXuoEC89QxaVlsg/5axYjCR0u', '2020-01-06 12:10:25.000', NULL);


INSERT INTO users (role, enabled, username, jobtitle, firstname, lastname, phone, email, password, datehired, datefired)
VALUES ('ROLE_EMPLOYEE', 'true', 'Newphew', 'VP', 'Barney', 'Rubble', '555-223-7890', 'rubbleB@Bedrockquarry.com',
'$2a$12$nGq7HpQYaNvrw5AUzNgrRuY/zl/sRPUEQ/RK.kZwtbhA5wFFEW2gC', '2020-01-06 12:20:35.000', NULL);


INSERT INTO users (role, enabled, username, jobtitle, firstname, lastname, phone, email, password, datehired, datefired)
VALUES ('ROLE_EMPLOYEE', 'true', 'Runner', 'HR_Manager', 'Willma', 'Flinstone', '555-333-1290', 'flintstoneWB@Bedrockquarry.com',
'$2a$12$MtKJxOuHXgGfyI.urGJ3.eEDClmvNa8u9gh9XXTCxcDdg9JxOpbIW', '2020-01-06 13:30:05.000', NULL);


INSERT INTO users (role, enabled, username, jobtitle, firstname, lastname, phone, email, password, datehired, datefired)
VALUES ('ROLE_EMPLOYEE', 'true', 'Soright', 'OP_Manager', 'Betty', 'Rubble', '555-445-1793', 'flintstoneB@Bedrockquarry.com',
'$2a$12$NN3AZwM1WBHbDpAoc5eCIehPM.LoZ4PoYYX4MGNtvvl8BJfa5XCXS', '2020-01-06 14:14:15.000', NULL);


INSERT INTO users (role, enabled, username, jobtitle, firstname, lastname, phone, email, password, datehired, datefired)
VALUES ('ROLE_ADMIN', 'true', 'Bossman', 'Sys_Manager', 'George', 'Slate', '555-404-6157', 'slateG@Bedrockquarry.com',
'$2a$12$sifyIxSWsggrPG2wLEL32.8d3AxwkR/fq.AZ.kEtjWtcnm40X5TIq', '2020-01-06 16:17:18.000', NULL);

DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities
(
    username          VARCHAR(50) NOT NULL,
    authority         VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username)
        REFERENCES public.users (username) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

INSERT INTO authorities (username, authority) VALUES ('Fatfreddy', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('Newphew', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('Runner', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('Soright', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('Bossman', 'ROLE_ADMIN');


DROP TABLE IF EXISTS tickets;
CREATE TABLE tickets
(
  ticketid            SERIAL PRIMARY KEY,
  description         VARCHAR(255) NOT NULL,
  ticketstatus        VARCHAR(16) NOT NULL,
  amount              NUMERIC(7,2) NOT NULL,
  datesubmitted       TIMESTAMP NOT NULL,
  dateresolved        TIMESTAMP,
  reimbursement_type  VARCHAR(32) NOT NULL,
  employeeid          INTEGER NOT NULL,
  CONSTRAINT fk_employ FOREIGN KEY(employeeid) REFERENCES users(employeeid) ON DELETE CASCADE
);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Took Client to industry convention', 'Approved', 750.25, '2024-01-02 9:00:00', null, 'Business-Trip', 1);

INSERT INTO tickets  (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Took Client to industry museum', 'Approved', 150.13, '2024-02-03 10:00:00', null, 'Continuing-Education', 1);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Took Client to business meeting', 'Pending', 1357.19, '2024-02-03 13:15:00', null, 'Business-Trip', 1);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Took Client to night club', 'Denied', 120.11, '2024-03-13 22:15:07', null, 'Entertainment', 1);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Met Client at research facility', 'Pending', 220.09, '2024-03-27 14:51:51', null, 'Continuing-Education', 1);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Recruiting new talent', 'Pending', 1221.19, '2024-04-02 08:30:01', null, 'Advertising', 2);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Make Speech at hiring event', 'Approved', 80.88, '2024-04-21 09:00:00', null, 'Advertising', 2);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Attended out of town legal consultation', 'Approved', 60.03, '2024-04-28 15:30:00', null, 'Legal', 2);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Local phone call to client', 'Denied', 150.27, '2024-04-28 17:30:45', null, 'Telephone', 2);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Purchase Training Supplies', 'Approved', 2531.71, '2024-05-01 17:30:45', null, 'Office-Expenses', 3);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Purchase Online Training Platform', 'Approved', 4950.77, '2024-05-20 11:20:15', null, 'Continuing-Education', 3);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Utilizie Background Service', 'Approved', 3760.66, '2024-05-30 13:00:00', null, 'Background-Check', 3);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Contractor for facility maintenance', 'Approved', 21180.03, '2024-06-01 07:00:00', null, 'Maintenance', 4);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Purchase food for Contractor', 'Pending', 350.50, '2024-06-06 12:00:00', null, 'Food', 4);

INSERT INTO tickets (description, ticketstatus, amount, datesubmitted, dateresolved, reimbursement_type, employeeid)
VALUES ('Purchase company mobile devices', 'Approved', 7878.50, '2024-06-11 16:20:22', null, 'Equipment', 5);
