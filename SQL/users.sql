CREATE DATABASE   tiw;

DROP  view IF EXISTS  auctiondata;
DROP TABLE IF EXISTS  offerts;
DROP  TABLE IF EXISTS  auctions;
DROP  TABLE IF EXISTS users;
DROP  TABLE IF EXISTS  salesItems;


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


CREATE TABLE salesItems(
	id int NOT NULL AUTO_INCREMENT,
    name 		varchar(20),
    description varchar(100),
    fileFormat  varchar(10),
    PRIMARY KEY(id)
);

insert into salesItems (name,description,fileFormat) values ("Gum Gum nomi","Frutto del diavolo GumGumNomi dal mondo di onepice","png");
insert into salesItems (name,description,fileFormat) values ("Death Note","Libro dello shinigami, permette di uccidere chiunque scrivendo il suo nome","png");
insert into salesItems (name,description,fileFormat) values ("500","Fiat 500","png");

select * from salesItems;





CREATE TABLE auctions(
	id int NOT NULL AUTO_INCREMENT,
    userid 			int,
    salesItemid 	int,
    initialPrize 	int,
    minimumOffer 	int,
    expiringDate    datetime, 
    PRIMARY KEY(id),
	FOREIGN  KEY (userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN  KEY (salesItemid) REFERENCES salesitems(id) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into auctions (userid,salesItemid,initialPrize,minimumOffer,expiringDate)
	          values (1,1,1,1,"2021-04-01 19:30");      
insert into auctions (userid,salesItemid,initialPrize,minimumOffer,expiringDate)
	          values (2,2,5,4,"2021-04-01 19:30");      
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

drop view if exists auctionsdata;
create view auctionsData as 
(	select auctions.id as auctionid,salesitems.id as itemId,auctions.userid,name,description,initialPrize,minimumOffer,expiringDate,fileFormat
	from auctions natural join salesitems
);

select max(id) as id from salesItems;
select * from salesitems;
select * from auctions;
select * from auctionsData