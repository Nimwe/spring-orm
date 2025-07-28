
DROP TABLE if exists public.account CASCADE;

/* => : ajouter la création de la table "client" */

CREATE TABLE client (
	id UUID PRIMARY KEY, /* GUID */
	first_name VARCHAR(100),
	last_name VARCHAR(50),
	email VARCHAR(50),
	birthdate date
);

CREATE TABLE account (
	id SERIAL PRIMARY KEY,
	client_id UUID NOT NULL, 
	creation_time timestamp,
	balance bigint,
	active boolean NOT NULL DEFAULT true,
	FOREIGN KEY (client_id) REFERENCES client(id)
);



