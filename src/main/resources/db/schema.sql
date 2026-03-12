CREATE TABLE quantity_measurement_entity (

id INT AUTO_INCREMENT PRIMARY KEY,

operation_type VARCHAR(50),

measurement_type VARCHAR(50),

value1 DOUBLE,

unit1 VARCHAR(20),

value2 DOUBLE,

unit2 VARCHAR(20),

result DOUBLE,

timestamp TIMESTAMP

);