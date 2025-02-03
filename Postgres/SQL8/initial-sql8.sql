-- Invoked as psql -f initial-sql8.sql
--
-- Make sure that Postgres is running!
--

drop database sql8;
create database sql8;
\connect sql8;

create table AUTHOR (
  ssn char(9) primary key,
  city varchar(50),
  name varchar(100)
);

create table PUBLISHER (
  name varchar(50) primary key,
  city varchar(50)
);

create table BOOK (
  isbn char(13) primary key,
  title varchar(100),
  publisher varchar(50),
  year char(4)
);  

create table WROTE (
  isbn char(13),
  ssn char(9),
  primary key(isbn, ssn)
);  
