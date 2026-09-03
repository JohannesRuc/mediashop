INSERT INTO products (id, name, description, price) VALUES
  ('p-1', 'Vinyl Sampler 2024', '<p>Kompilation mit 12 Titeln</p>', 24.90),
  ('p-2', 'Hoerbuch Abo', '<p>Monatliches Abo, jederzeit kuendbar</p>', 9.99);

INSERT INTO orders (id, customer_id, status, total_amount, created_at) VALUES
  ('ord-1', 'cust-42', 'PAID', 24.90, TIMESTAMP '2024-11-01 10:15:00'),
  ('ord-2', 'cust-77', 'OPEN', 9.99, TIMESTAMP '2024-11-04 08:02:00');
