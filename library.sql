--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bookhistories; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE bookhistories (
    id integer NOT NULL,
    personid integer,
    datecheckedout timestamp without time zone,
    duedate timestamp without time zone,
    datereturned timestamp without time zone,
    libraryitemsid integer
);


ALTER TABLE bookhistories OWNER TO "Guest";

--
-- Name: bookhistories_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE bookhistories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bookhistories_id_seq OWNER TO "Guest";

--
-- Name: bookhistories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE bookhistories_id_seq OWNED BY bookhistories.id;


--
-- Name: libraryitems; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE libraryitems (
    id integer NOT NULL,
    type character varying,
    title character varying,
    genre character varying,
    author character varying,
    artist character varying,
    issuenumber integer
);


ALTER TABLE libraryitems OWNER TO "Guest";

--
-- Name: libraryitems_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE libraryitems_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE libraryitems_id_seq OWNER TO "Guest";

--
-- Name: libraryitems_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE libraryitems_id_seq OWNED BY libraryitems.id;


--
-- Name: persons; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE persons (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE persons OWNER TO "Guest";

--
-- Name: persons_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE persons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE persons_id_seq OWNER TO "Guest";

--
-- Name: persons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE persons_id_seq OWNED BY persons.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY bookhistories ALTER COLUMN id SET DEFAULT nextval('bookhistories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY libraryitems ALTER COLUMN id SET DEFAULT nextval('libraryitems_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY persons ALTER COLUMN id SET DEFAULT nextval('persons_id_seq'::regclass);


--
-- Data for Name: bookhistories; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY bookhistories (id, personid, datecheckedout, duedate, datereturned, libraryitemsid) FROM stdin;
\.


--
-- Name: bookhistories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('bookhistories_id_seq', 1, false);


--
-- Data for Name: libraryitems; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY libraryitems (id, type, title, genre, author, artist, issuenumber) FROM stdin;
\.


--
-- Name: libraryitems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('libraryitems_id_seq', 1, false);


--
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY persons (id, name) FROM stdin;
\.


--
-- Name: persons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('persons_id_seq', 1, false);


--
-- Name: bookhistories_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY bookhistories
    ADD CONSTRAINT bookhistories_pkey PRIMARY KEY (id);


--
-- Name: libraryitems_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY libraryitems
    ADD CONSTRAINT libraryitems_pkey PRIMARY KEY (id);


--
-- Name: persons_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

