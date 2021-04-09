CREATE DATABASE  tiw;

DROP  TABLE users;
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
