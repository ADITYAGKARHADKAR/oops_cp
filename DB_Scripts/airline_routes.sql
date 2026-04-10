drop table if exists airline_routes;

CREATE TABLE airline_routes (
                                air_route_id INT PRIMARY KEY,
                                route_id INT,
                                airline_code VARCHAR(10),
                                airline_name VARCHAR(100),
                                day_of_week VARCHAR(50),

                                FOREIGN KEY (route_id) REFERENCES routes(route_id)
);

INSERT INTO airline_routes
(air_route_id, route_id, airline_code, airline_name, day_of_week)
VALUES
    (1,1,'AI','Air India','Monday,Wednesday'),
    (2,2,'AI','Air India','Tuesday,Friday'),
    (3,3,'AF','Air France','Wednesday'),
    (4,4,'SQ','Singapore Airlines','Monday,Thursday'),
    (5,5,'BA','British Airways','Friday'),
    (6,6,'EK','Emirates','Saturday'),
    (7,7,'6E','Indigo','Daily'),
    (8,8,'TG','Thai Airways','Sunday'),
    (9,9,'IB','Iberia','Tuesday'),
    (10,10,'CX','Cathay Pacific','Wednesday,Saturday'),

    (11,11,'AI','Air India','Monday,Wednesday'),
    (12,12,'AI','Air India','Tuesday,Friday'),
    (13,13,'AF','Air France','Wednesday'),
    (14,14,'SQ','Singapore Airlines','Monday,Thursday'),
    (15,15,'BA','British Airways','Friday'),
    (16,16,'EK','Emirates','Saturday'),
    (17,17,'6E','Indigo','Daily'),
    (18,18,'TG','Thai Airways','Sunday'),
    (19,19,'IB','Iberia','Tuesday'),
    (20,20,'CX','Cathay Pacific','Wednesday,Saturday'),

    (21,1,'JL','Japan Airlines','Monday'),
    (22,2,'AA','American Airlines','Thursday'),
    (23,3,'LH','Lufthansa','Wednesday'),
    (24,4,'QF','Qantas','Friday'),
    (25,5,'AC','Air Canada','Sunday'),
    (26,6,'AA','American Airlines','Saturday'),
    (27,7,'UK','Vistara','Daily'),
    (28,8,'6E','Indigo','Tuesday'),
    (29,9,'IZ','Italian National Airline','Wednesday'),
    (30,10,'KE','Korean Airline','Friday'),

    (31,11,'JL','Japan Airlines','Daily'),
    (32,12,'AA','American Airlines','Daily'),
    (33,13,'LH','Lufthansa','Daily'),
    (34,14,'QF','Qantas','Daily'),
    (35,15,'AC','Air Canada','Daily'),
    (36,16,'AA','American Airlines','Daily'),
    (37,17,'UK','Vistara','Daily'),
    (38,18,'6E','Indigo','Daily'),
    (39,19,'IZ','Italian National Airline','Daily'),
    (40,20,'KE','Korean Airline','Daily'),

    (41,21,'AI','Air India','Daily'),
    (42,21,'AA','American Airlines','Daily'),
    (43,22,'AI','Air India','Daily'),
    (44,22,'AA','American Airlines','Daily');