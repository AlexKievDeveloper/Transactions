CREATE DATABASE "Transactions"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE public.transactions_table
(
    id integer NOT NULL DEFAULT nextval('transactions_table_id_seq'::regclass),
    invoiceinto integer NOT NULL,
    invoiceto integer NOT NULL,
    status character varying(20) COLLATE pg_catalog."default" NOT NULL,
    amount double precision NOT NULL,
    CONSTRAINT transactions_table_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.transactions_table
    OWNER to postgres;




INSERT INTO public.transactions_table(invoiceinto, invoiceto, status, amount) VALUES (?,?,?,?);

SELECT id, invoiceinto, invoiceto, status, amount FROM public.transactions_table;