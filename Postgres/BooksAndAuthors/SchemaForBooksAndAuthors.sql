\connect booksandauthors;

DROP TABLE authorISBN;
DROP TABLE titles;
DROP TABLE authors;

CREATE TABLE authors (
   authorID SERIAL PRIMARY KEY,
   firstName varchar (20) NOT NULL,
   lastName varchar (30) NOT NULL
);

CREATE TABLE titles (
   isbn varchar (20) NOT NULL,
   title varchar (100) NOT NULL,
   editionNumber INT NOT NULL,
   copyright varchar (4) NOT NULL,
   PRIMARY KEY (isbn)
);

CREATE TABLE authorISBN (
   authorID INT NOT NULL,
   isbn varchar (20) NOT NULL,
   FOREIGN KEY (authorID) REFERENCES authors (authorID), 
   FOREIGN KEY (isbn) REFERENCES titles (isbn)
);
