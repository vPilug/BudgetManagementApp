CREATE TABLE categories
(id uuid NOT NULL,
name varchar(100) NOT NULL,
CONSTRAINT categories_pkey PRIMARY KEY (id)
);

CREATE TABLE expenses
(id uuid NOT NULL,
date DATE NOT NULL,
amount decimal(10,2) NOT NULL,
description varchar(300),
category_id uuid NOT NULL,
CONSTRAINT expenses_pkey PRIMARY KEY (id),
CONSTRAINT expenses_category_id_fkey FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE incomes
(id uuid NOT NULL,
date DATE NOT NULL,
amount decimal(10,2) NOT NULL,
source varchar(60) NOT NULL,
CONSTRAINT incomes_pkey PRIMARY KEY (id)
);