Bus Booking System (Core Java Console)
=======================================
What this is:
- A simple, fresher-friendly console-based Bus Booking System written in Core Java.
- Modules included: User, Bus Info, Booking, Admin, Payment (dummy), simple file-based persistence using Java serialization.

How to run:
1. Ensure Java (JDK) is installed. (javac and java on PATH)
2. From the project root run:
   javac -d bin src/com/busbooking/**/*.java
   java -cp bin com.busbooking.Main

Note:
- Data files (.ser) will be created in the project root (data/ directory) on first run.
- Admin credentials (default):
    username: admin
    password: admin123
