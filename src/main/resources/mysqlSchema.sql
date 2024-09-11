CREATE TABLE employee (
    id INT NOT NULL AUTO_INCREMENT, -- Primary key and auto-increment
    name VARCHAR(255),              -- Name field
    age INT,                        -- Age field
    email VARCHAR(255),             -- Email field
    mobile_number VARCHAR(20),      -- Mobile number field
    PRIMARY KEY (id)                -- Setting id as primary key
);