# Bus Reservation System (Console, MySQL-backed)

## Overview
This is a console-based Java Bus Reservation System that uses MySQL for persistence.
Features:
- Admin login (can add/update buses)
- User registration & login (book/cancel/view bookings)
- SHA-256 password hashing (in Java) and sample hashed passwords in SQL (via SHA2)

## Setup
1. Install MySQL and create a user (or use root).
2. Run `schema.sql` using MySQL client:
   ```
   mysql -u root -p < schema.sql
   ```
3. Update `src/main/resources/db.properties` with your DB credentials.
4. Ensure MySQL Connector/J is on the classpath.
   - If using Maven, add the connector dependency.
   - Or download the `mysql-connector-java` jar and place it in your classpath.
5. Compile & run:
   ```
   javac -d out -cp .:mysql-connector-java-8.0.XX.jar $(find src/main/java -name "*.java")
   java -cp out:.:mysql-connector-java-8.0.XX.jar com.example.bus.Main
   ```
   (Windows: replace `:` with `;` in classpath)

## Notes
- Admin sample: username `admin`, password `admin123`.
- User samples: `anto@gmail.com` / `anto123`, `layo@gmail.com` / `layo123`.
- Admin-only actions: adding and updating buses.
- Booking updates `available_seats` atomically; cancellation restores seats.
