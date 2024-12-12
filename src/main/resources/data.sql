DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS accounts;
 
CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(250) NOT NULL,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250),
  image VARCHAR(250),
  category VARCHAR(250),
  price DECIMAL(5, 2) NOT NULL,
  quantity INTEGER,
  internal_reference VARCHAR(20),
  shell_id INTEGER,
  inventory_status ENUM ('INSTOCK', 'LOWSTOCK', 'OUTOFSTOCK'),
  rating FLOAT,
  created_at INT,
  updated_at INT
);

CREATE TABLE accounts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  firstname VARCHAR(250),
  email VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL
);

INSERT INTO products (code, name, description, image, category, price, quantity, internal_reference, shell_id,
inventory_status, rating, created_at, updated_at) VALUES
  ('TV_001', 'TCL', 'Television', null, '001', 300.00, 100, 'TV001', null, 'INSTOCK', 8.2, 1733942578, null),
  ('AUDIO_002', 'SAMSUNG', 'Audio Dolby', null, '002', 250.00, 20, 'AUDIO002', null, 'LOWSTOCK', 9.2, 1733942578, null),
  ('GAMING_003', 'SONY', '3D Gaming', null, '003', 500.00, 30, 'GAME003', null, 'OUTOFSTOCK', 10.5, 1733942578, null)