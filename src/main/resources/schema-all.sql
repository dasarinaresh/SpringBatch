DROP TABLE IF EXISTS airport;

CREATE TABLE airport  (
    airportCode VARCHAR(20) NOT NULL PRIMARY KEY,
    airportName VARCHAR(50),
    city VARCHAR(50),
    state VARCHAR(50),
    country VARCHAR(50),
    latitude DECIMAL,
    longitude DECIMAL
);