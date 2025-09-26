--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: task; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO task VALUES (1, 'Create a tasklist', 'Uncompleted', '2025-09-24');
INSERT INTO task VALUES (3, 'Task 3', 'completed', '2025-09-27');
INSERT INTO task VALUES (4, 'Task 4', 'uncompleted', '2025-09-29');
INSERT INTO task VALUES (2, 'Task 2', 'uncompleted', '2025-09-26');
INSERT INTO task VALUES (5, 'Task 5', 'uncompleted', '2025-09-30');


--
-- Name: task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('task_id_seq', 5, true);


--
-- PostgreSQL database dump complete
--

