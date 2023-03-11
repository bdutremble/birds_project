
DROP TABLE IF EXISTS shape;
DROP TABLE IF EXISTS size;
DROP TABLE IF EXISTS behaviors;
DROP TABLE IF EXISTS habitats;
DROP TABLE IF EXISTS colors;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS birds;


CREATE TABLE shapes (
  model_pk int unsigned NOT NULL AUTO_INCREMENT,
  model_id enum('GRAND_CHEROKEE', 'CHEROKEE', 'COMPASS', 'RENEGADE', 'WRANGLER', 'GLADIATOR', 'WRANGLER_4XE') NOT NULL,
  trim_level varchar(40) NOT NULL,
  num_doors int NOT NULL,
  wheel_size int NOT NULL,
  base_price decimal(9, 2) NOT NULL,
  PRIMARY KEY (model_pk),
  UNIQUE KEY (model_id, trim_level, num_doors)
);

CREATE TABLE images (
  image_pk int unsigned NOT NULL AUTO_INCREMENT,
  model_fk int unsigned NOT NULL,
  image_id varchar(40) NOT NULL,
  width int NOT NULL,
  height int NOT NULL,
  mime_type enum('image/jpeg', 'image/png'),
  name varchar(256),
  data mediumblob NOT NULL,
  PRIMARY KEY (image_pk),
  FOREIGN KEY (model_fk) REFERENCES models (model_pk)
);

CREATE TABLE colors (
  color_pk int unsigned NOT NULL AUTO_INCREMENT,
  color_id varchar(30) NOT NULL,
  color varchar(60) NOT NULL,
  price decimal(9, 2) NOT NULL,
  is_exterior boolean NOT NULL,
  PRIMARY KEY (color_pk),
  UNIQUE KEY (color_id)
);

CREATE TABLE habitats (
  engine_pk int unsigned NOT NULL AUTO_INCREMENT,
  engine_id varchar(30) NOT NULL,
  size_in_liters decimal(5, 2) NOT NULL,
  name varchar(60) NOT NULL,
  fuel_type enum('GASOLINE', 'DIESEL', 'HYBRID') NOT NULL,
  mpg_city decimal(7, 2) NOT NULL,
  mpg_hwy decimal(7, 2) NOT NULL,
  has_start_stop boolean NOT NULL,
  description varchar(500) NOT NULL,
  price decimal(9, 2),
  PRIMARY KEY (engine_pk),
  UNIQUE KEY (engine_id)
);

CREATE TABLE behaviors (
  tire_pk int unsigned NOT NULL AUTO_INCREMENT,
  tire_id varchar(30) NOT NULL, 
  tire_size varchar(128) NOT NULL,
  manufacturer varchar(70) NOT NULL,
  price decimal(7, 2) NOT NULL,
  warranty_miles int NOT NULL,
  PRIMARY KEY (tire_pk),
  UNIQUE KEY (tire_id)
);

CREATE TABLE size (
  option_pk int unsigned NOT NULL AUTO_INCREMENT,
  option_id varchar(30) NOT NULL,
  category enum('DOOR', 'EXTERIOR', 'INTERIOR', 'STORAGE', 'TOP', 'WHEEL') NOT NULL,
  manufacturer varchar(60) NOT NULL,
  name varchar(60) NOT NULL,
  price decimal(9, 2) NOT NULL,
  PRIMARY KEY (option_pk),
  UNIQUE KEY (option_id)
);

CREATE TABLE birds (
  order_pk int unsigned NOT NULL AUTO_INCREMENT,
  customer_fk int unsigned NOT NULL,
  color_fk int unsigned NOT NULL,
  engine_fk int unsigned NOT NULL,
  tire_fk int unsigned NOT NULL,
  model_fk int unsigned NOT NULL,
  price decimal(9, 2) NOT NULL,
  PRIMARY KEY (order_pk),
  FOREIGN KEY (customer_fk) REFERENCES customers (customer_pk) ON DELETE CASCADE,
  FOREIGN KEY (color_fk) REFERENCES colors (color_pk) ON DELETE CASCADE,
  FOREIGN KEY (engine_fk) REFERENCES engines (engine_pk) ON DELETE CASCADE,
  FOREIGN KEY (tire_fk) REFERENCES tires (tire_pk) ON DELETE CASCADE,
  FOREIGN KEY (model_fk) REFERENCES models (model_pk) ON DELETE CASCADE
);