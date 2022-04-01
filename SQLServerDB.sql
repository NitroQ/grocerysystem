CREATE TABLE Inventory(
	sku INT PRIMARY KEY NOT NULL,
	prod_name VARCHAR(55) NOT NULL,
	--inv_status VARCHAR(10) NOT NULL CHECK (inv_status IN('active', 'inactive')) DEFAULT 'active',
	qty int NOT NULL,
	price decimal(10, 2) NOT NULL,
	cost decimal(10, 2) NOT NULL
);
--SELECT * FROM Inventory WHERE sku LIKE '%keyword1%' OR prod_name LIKE '%keyword1%'
CREATE TABLE Employee(
	emp_id INT PRIMARY KEY IDENTITY(2000, 1),
	fname VARCHAR(50) NOT NULL,
	lname VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL UNIQUE,
	empaddress VARCHAR(150) NOT NULL,
	empstatus VARCHAR(10) NOT NULL CHECK (empstatus IN('active', 'inactive')) DEFAULT 'active',
	position VARCHAR (10) NOT NULL CHECK (position IN('merch', 'cashier', 'manager')),
	gender VARCHAR(1),
	age INT NOT NULL,
	created_at DATETIME DEFAULT(GETDATE()),
);
INSERT INTO Employee (fname, lname, email, empaddress, position, gender, age) VALUES ('Frank Vincent','Gesmundo','frankvgesmundo@gmail.com', 'Blk6 Lot 17 Metro Royale', 'Manager', 'M', '19');
--DELETE FROM Employee WHERE id = 2001;
--SELECT * FROM Employee;

CREATE TABLE Users(
	id INT PRIMARY KEY IDENTITY(1, 1),
	emp_id INT FOREIGN KEY(emp_id) REFERENCES Employee(emp_id) NOT NULL,
	username VARCHAR(20) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	user_type VARCHAR(5) NOT NULL CHECK (user_type IN('admin', 'emp'))
);
INSERT INTO Users (emp_id, username, password, user_type) VALUES (2000, 'frankie', 'frankie123', 'admin');
--SELECT * FROM Users; WHERE username = 'frankie' AND password = 'frankie123';
--DROP TABLE Users;

CREATE TABLE Sales(
	sale_id INT PRIMARY KEY IDENTITY(1, 1),
	emp_id INT FOREIGN KEY(emp_id) REFERENCES Employee(emp_id) NOT NULL,
	payment VARCHAR (10) CHECK (payment IN('cash', 'visa', 'mastercard')) NOT NULL,
	discount VARCHAR (10) CHECK (discount IN('pwd', 'senior', 'member')),
	total decimal(10, 2) NOT NULL,
	payment_amt decimal(10, 2) NOT NULL,
	change decimal(10, 2) NOT NULL,
	profit decimal(10, 2),
	sale_date DATETIME DEFAULT(GETDATE()),
);
SELECT * FROM Sales;

CREATE TABLE Sold_Items(
	sale_id INT FOREIGN KEY(sale_id) REFERENCES Sales(sale_id) NOT NULL ,
	sku INT FOREIGN KEY(sku) REFERENCES Inventory(sku) NOT NULL,
	qty INT NOT NULL,
	item_total decimal(10, 2) NOT NULL
);

--SELECT * FROM Sold_Items;

CREATE TABLE Logs(
	id INT PRIMARY KEY IDENTITY(1, 1),
	emp_id INT FOREIGN KEY(emp_id) REFERENCES Employee(emp_id) NOT NULL,
	family VARCHAR (10) CHECK (family IN('insert', 'update', 'disable')) NOT NULL,
	loc VARCHAR(50) NOT NULL,
	remarks VARCHAR(200),
	log_date DATETIME DEFAULT(GETDATE())
);

Select * From Sales;
