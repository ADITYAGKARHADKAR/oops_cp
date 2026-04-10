drop table if exists Ticket;

CREATE TABLE Ticket (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        PassengerName VARCHAR(100) NOT NULL,
                        PassportNumber VARCHAR(50) NOT NULL,
                        age INT,
                        travel_date DATE NOT NULL,
                        AirlineCode VARCHAR(10) NOT NULL,
                        AirlineName VARCHAR(100),
                        Departure TIME,
                        Arrival TIME,
                        price DOUBLE,
                        discount DOUBLE,
                        fare DOUBLE,
                        pnr VARCHAR(50) UNIQUE NOT NULL
);