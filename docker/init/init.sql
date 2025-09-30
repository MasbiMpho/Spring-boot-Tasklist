SET search_path = public;

CREATE TABLE IF NOT EXISTS task (
    id BIGSERIAL NOT NULL,
    name character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    due_date date
);

INSERT INTO task (name, status, due_date) VALUES
('Create database', 'completed', '2025-09-24'), 
('Create a spring boot tasklist', 'completed', '2025-09-25'),
('Create REST API endpoints', 'completed', '2025-09-26'),
('Test API endpoints using postman',	'uncompleted',	'2025-09-27'),
('Implement Swagger', 'uncompleted', '2025-09-28'),
('Make data persistent', 'uncompleted', '2025-09-30');


SELECT setval(pg_get_serial_sequence('public.task', 'id'), (SELECT MAX(id) FROM public.task));

