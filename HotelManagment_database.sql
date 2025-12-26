create database  HotelManagment_database;
use HotelManagment_database;
Create TABLE  login (
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

insert into login (username, password) values('admin', 'admin123');

Create TABLE  customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL
);

Create table rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    availability BOOLEAN NOT NULL DEFAULT TRUE
);


Create table   bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    booking_date DATE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL CHECK (total_amount > 0),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

Create table  payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    payment_date DATE NOT NULL,
    amount_paid DECIMAL(10, 2) NOT NULL CHECK (amount_paid > 0),
    payment_method VARCHAR(50) NOT NULL,
    payment_status ENUM('Pending', 'Completed', 'Failed') NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

Create table   reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    reservation_status ENUM('Pending', 'Confirmed', 'Cancelled') NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);




insert into customers (first_name, last_name, email, phone, address) 
VALUES
('Nimra', 'Shaikh', 'nimra.shk@hotmail.com', '1234567890', '123 Elm Street'),
('Hajira', 'Hussain', 'hajira.hussain@gmail.com', '1234567891', '456 Oak Avenue'),
('Michael', 'Johnson', 'michael.johnson@yahoo.com', '1234567892', '789 Pine Road'),
('Emily', 'Davis', 'emily.davis@yahoo.com', '1234567893', '101 Maple Street'),
('Iqra', 'Soomro', 'iqra.soomro@gmail.com', '1234567894', '202 Birch Blvd'),
('Sophia', 'Taylor', 'sophia.taylor@hotmail.com', '1234567895', '303 Cedar Crescent'),
('Shiza', 'Shaikh', 'shiza.shaikh@hotmail.com', '1234567896', '404 Willow Way'),
('Hiba', 'Noor', 'hiba.noor@gmail.com', '1234567897', '505 Pinehill Drive');


insert into rooms (room_type, price, availability)
VALUES
('Single', 100.00, TRUE),
('Double', 150.00, TRUE),
('Suite', 250.00, TRUE);


Insert into bookings (customer_id, room_id, check_in_date, check_out_date, booking_date, total_amount)
VALUES
(1, 1, '2025-06-01', '2025-06-05', '2025-05-15', 500.00),
(2, 2, '2025-06-10', '2025-06-15', '2025-05-18', 900.00),
(3, 3, '2025-06-12', '2025-06-18', '2025-05-20', 1250.00),
(4, 1, '2025-06-08', '2025-06-12', '2025-05-25', 500.00),
(5, 2, '2025-06-15', '2025-06-20', '2025-05-28', 900.00),
(6, 3, '2025-06-18', '2025-06-22', '2025-06-01', 1250.00),
(7, 1, '2025-06-20', '2025-06-25', '2025-06-03', 500.00),
(8, 2, '2025-06-25', '2025-06-30', '2025-06-05', 900.00);


Insert into payments (booking_id, payment_date, amount_paid, payment_method, payment_status)
VALUES
(1, '2025-05-30', 500.00, 'Credit Card', 'Completed'),
(2, '2025-05-30', 900.00, 'Cash', 'Completed'),
(3, '2025-05-30', 1250.00, 'Bank Transfer', 'Completed'),
(4, '2025-06-02', 500.00, 'Debit Card', 'Completed'),
(5, '2025-06-05', 900.00, 'Credit Card', 'Completed'),
(6, '2025-06-10', 1250.00, 'Cash', 'Completed'),
(7, '2025-06-15', 500.00, 'Cash', 'Completed'),
(8, '2025-06-18', 900.00, 'Bank Transfer', 'Completed');


Insert into reservations (room_id, check_in_date, check_out_date, reservation_status)
VALUES
(1, '2025-06-05', '2025-06-10', 'Confirmed'),
(2, '2025-06-15', '2025-06-20', 'Pending'),
(3, '2025-06-20', '2025-06-25', 'Confirmed');

select * from customers;
insert into customers(first_name, last_name, email, phone, address) values 
('Zain', 'Malik', 'zain.malik@gmail.com', '0231-8976543', 'Defence Phase 08');

select * from customers;

update customers set phone = '0234-988877', address = 'Clifton block 5'
where customer_id = 3;
select * from customers;

select * from  rooms;
update rooms set price = 5000 , availability = FALSE where room_id = 2;
select * from  rooms;

insert into bookings (customer_id, room_id, check_in_date, check_out_date, booking_date, total_amount)
values (1, 3, '2025-07-01', '2025-07-05', '2025-06-10', 3000.00);
select * from bookings;

Delete from bookings where booking_id = 3;









