-- Schema for bus_reservation
CREATE DATABASE IF NOT EXISTS bus_reservation;
USE bus_reservation;

-- admins
CREATE TABLE IF NOT EXISTS admins (
  admin_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(64) NOT NULL
);

-- users
CREATE TABLE IF NOT EXISTS users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password_hash VARCHAR(64) NOT NULL,
  phone VARCHAR(15)
);

-- buses
CREATE TABLE IF NOT EXISTS buses (
  bus_id INT AUTO_INCREMENT PRIMARY KEY,
  bus_name VARCHAR(100) NOT NULL,
  bus_type VARCHAR(50) NOT NULL,
  source VARCHAR(100) NOT NULL,
  destination VARCHAR(100) NOT NULL,
  total_seats INT NOT NULL,
  available_seats INT NOT NULL,
  fare_per_seat DECIMAL(10,2) NOT NULL
);

-- bookings
CREATE TABLE IF NOT EXISTS bookings (
  booking_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  bus_id INT NOT NULL,
  num_seats INT NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,
  booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (bus_id) REFERENCES buses(bus_id) ON DELETE CASCADE
);

-- Sample admin
INSERT INTO admins (username, password_hash) VALUES
('admin', SHA2('admin123',256));

-- Sample users
INSERT INTO users (name, email, password_hash, phone) VALUES
('Anto','anto@gmail.com', SHA2('anto123',256), '9876543210'),
('Layo','layo@gmail.com', SHA2('layo123',256), '9876501234');

-- Sample buses with types and fares (AC, Non-AC, AC Sleeper, Non-AC Sleeper, AC Seater, Non-AC Seater)
INSERT INTO buses (bus_name, bus_type, source, destination, total_seats, available_seats, fare_per_seat) VALUES
('SRS Travels','AC','Chennai','Bangalore',40,40,800.00),
('KPN Travels','Non-AC','Madurai','Coimbatore',45,45,500.00),
('Parveen Travels','AC Sleeper','Trichy','Chennai',30,30,1000.00),
('Gamma Travels','Non-AC Seater','Coimbatore','Bangalore',52,52,600.00);

-- Sample booking
INSERT INTO bookings (user_id, bus_id, num_seats, total_amount) VALUES
(1, 1, 2, 1600.00);
