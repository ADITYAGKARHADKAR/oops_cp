drop table if exists routes;

CREATE TABLE routes (
                        route_id INT PRIMARY KEY,
                        from_city VARCHAR(50),
                        from_country VARCHAR(50),
                        to_city VARCHAR(50),
                        to_country VARCHAR(50)
);


INSERT INTO routes (route_id, from_city, from_country, to_city, to_country) VALUES
                                                                                  (1, 'Mumbai', 'India', 'Tokyo', 'Japan'),
                                                                                (2, 'Delhi', 'India', 'New York', 'USA'),
                                                                                (3, 'Paris', 'France', 'Berlin', 'Germany'),
                                                                                (4, 'Sydney', 'Australia', 'Singapore', 'Singapore'),
                                                                                (5, 'Toronto', 'Canada', 'London', 'UK'),
                                                                                (6, 'Dubai', 'UAE', 'San Francisco', 'USA'),
                                                                                (7, 'Nagpur', 'India', 'Pune', 'India'),
                                                                                (8, 'Chennai', 'India', 'Bangkok', 'Thailand'),
                                                                                (9, 'Rome', 'Italy', 'Madrid', 'Spain'),
                                                                                (10, 'Seoul', 'South Korea', 'Beijing', 'China'),

                                                                                (11, 'Tokyo', 'Japan', 'Mumbai', 'India'),
                                                                                (12, 'New York', 'USA', 'Delhi', 'India'),
                                                                                (13, 'Berlin', 'Germany', 'Paris', 'France'),
                                                                                (14, 'Singapore', 'Singapore', 'Sydney', 'Australia'),
                                                                                (15, 'London', 'UK', 'Toronto', 'Canada'),
                                                                                (16, 'San Francisco', 'USA', 'Dubai', 'UAE'),
                                                                                (17, 'Pune', 'India', 'Nagpur', 'India'),
                                                                                (18, 'Bangkok', 'Thailand', 'Chennai', 'India'),
                                                                                (19, 'Madrid', 'Spain', 'Rome', 'Italy'),
                                                                                (20, 'Beijing', 'China', 'Seoul', 'South Korea'),
                                                                                (21,'Mumbai','India','Los Angeles','USA'),
                                                                                (22,'Los Angeles','USA','Mumbai','India');