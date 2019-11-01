CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
	role VARCHAR(10),
	active BOOLEAN NOT NULL DEFAULT 0,
 	locked BOOLEAN NOT NULL DEFAULT 0,
 	blocked BOOLEAN NOT NULL DEFAULT 0,
 	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 	modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    UNIQUE(email)
);