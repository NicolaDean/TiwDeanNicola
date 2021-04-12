CREATE DATABASE   tiw;

DROP  TABLE IF EXISTS users;
CREATE TABLE users(
	id int NOT NULL AUTO_INCREMENT,
    email 		varchar(20),
    password 	varchar(20),
    name 		varchar(20),
    PRIMARY KEY(id)
    
);

insert into users (email,password,name) values ("nicola@gmail.com","1234","Nicola");
insert into users (email,password,name) values ("paolo@gmail.com","1234","Paolo");
insert into users (email,password,name) values ("davide@gmail.com","1234","Davide");
insert into users (email,password,name) values ("fabio@gmail.com","1234","Fabio");

select * from users;

DROP  TABLE IF EXISTS  salesItems;
CREATE TABLE salesItems(
	id int NOT NULL AUTO_INCREMENT,
    name 		varchar(20),
    description varchar(100),
    imagePath 	varchar(50),
    PRIMARY KEY(id)
    
);

insert into salesItems (name,description,imagePath) values ("Gum Gum nomi","Frutto del diavolo GumGumNomi dal mondo di onepice","/nicola/gumgum.png");
insert into salesItems (name,description,imagePath) values ("Death Note","Libro dello shinigami, permette di uccidere chiunque scrivendo il suo nome","/nicola/deathnote.png");
insert into salesItems (name,description,imagePath) values ("500","Fiat 500","/nicola/500.jpg");
insert into salesItems (name,description,imagePath) values ("S","Fiat 500","/nicola/500.jpg");

select * from salesItems;

DROP  TABLE IF EXISTS  auctions;
CREATE TABLE auctions(
	id int NOT NULL AUTO_INCREMENT,
    userid 			int,
    salesItemid 	int,
    initialPrize 	int,
    minimumOffer 	int,
    espiringDate    TIMESTAMP, 
    PRIMARY KEY(id),
	FOREIGN  KEY (userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN  KEY (salesItemid) REFERENCES salesitems(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS  offerts;
CREATE TABLE offerts(
	id int NOT NULL AUTO_INCREMENT,
    userid 			int,
    autionsid		int,
    offer			int,
    offerDate		TIMESTAMP,
    PRIMARY KEY(id),
	FOREIGN  KEY (userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN  KEY (autionsid) REFERENCES auctions(id) ON DELETE CASCADE ON UPDATE CASCADE
);



