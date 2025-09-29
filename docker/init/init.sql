SET search_path = public;

CREATE TABLE IF NOT EXISTS task (
    id BIGSERIAL NOT NULL,
    name character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    due_date date
);

INSERT INTO task (name, status, due_date) VALUES 
('Create a tasklist', 'Uncompleted', '2025-09-24'),
('Task 3',	'completed',	'2025-09-27'),
('Task 4', 'uncompleted', '2025-09-29'),
('Task 2', 'uncompleted', '2025-09-26'),
('Task 5', 'uncompleted', '2025-09-30');



SELECT setval(pg_get_serial_sequence('public.task', 'id'), (SELECT MAX(id) FROM public.task));

