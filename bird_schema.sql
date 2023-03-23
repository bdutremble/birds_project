
DROP TABLE IF EXISTS bird_habitat;
DROP TABLE IF EXISTS diet;
DROP TABLE IF EXISTS observation;
DROP TABLE IF EXISTS coloration;
DROP TABLE IF EXISTS bird_size;
DROP TABLE IF EXISTS nest_type;
DROP TABLE IF EXISTS bird;
DROP TABLE IF EXISTS food;
DROP TABLE IF EXISTS habitat;

CREATE TABLE habitat (
  habitat_id int unsigned NOT NULL AUTO_INCREMENT,
  habitat_type varchar(30) NOT NULL,
  habitat_description text,
  PRIMARY KEY (habitat_id)
);

CREATE TABLE food (
  food_pk int unsigned NOT NULL AUTO_INCREMENT,
  food_name varchar(80),
  food_type set('FRUIT', 'SEED', 'INVERTEBRATES','RODENTS', 'FISH') NOT NULL,
  PRIMARY KEY (food_pk)
);

CREATE TABLE bird (
  bird_id int unsigned NOT NULL AUTO_INCREMENT,
  common_name varchar(40) NOT NULL,
  scientific_name varchar(80),
  bird_sex_id varchar(10),
  PRIMARY KEY (bird_id)
);

CREATE TABLE nest_type (
  nest_type_pk int unsigned NOT NULL AUTO_INCREMENT,
  bird_id int unsigned NOT NULL,
  nest_type_name enum('GROUND', 'PLATFORM', 'CAVITY', 'BOWL', 'WILD'),
  nest_description text,
  PRIMARY KEY (nest_type_pk),
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE
);

CREATE TABLE bird_size (
  bird_size_pk int unsigned NOT NULL AUTO_INCREMENT,
  bird_id int unsigned NOT NULL,
  bird_size_id enum('SMALL', 'MEDIUM', 'LARGE'),
  wingspan_cm int unsigned,
  PRIMARY KEY (bird_size_pk),
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE
);

CREATE TABLE coloration (
  coloration_pk int unsigned NOT NULL AUTO_INCREMENT,
  bird_id int unsigned NOT NULL,
  beak_color_id varchar(30),
  head_color_id varchar(30),
  torso_color_id varchar(30),
  wing_color_id varchar(30),
  PRIMARY KEY (coloration_pk),
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE
);

CREATE TABLE observation (
  observation_pk int unsigned NOT NULL AUTO_INCREMENT,
  bird_id int unsigned NOT NULL,
  observation_time TIMESTAMP NOT NULL,
  observation_location varchar(30) NOT NULL,
  PRIMARY KEY (observation_pk),
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE
);

CREATE TABLE diet (
  bird_id int unsigned NOT NULL,
  food_id int unsigned NOT NULL,
  CONSTRAINT diet_pk PRIMARY KEY (bird_id, food_id),
  CONSTRAINT diet_bird_fk
      FOREIGN KEY (bird_id) REFERENCES bird (bird_id),
  CONSTRAINT diet_food_fk 
      FOREIGN KEY (food_id) REFERENCES food (food_pk) ON DELETE CASCADE
);

CREATE TABLE bird_habitat (
  bird_id int unsigned NOT NULL,
  habitat_id int unsigned NOT NULL,
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE,
  FOREIGN KEY (habitat_id) REFERENCES habitat (habitat_id) ON DELETE CASCADE,
  UNIQUE KEY (bird_id, habitat_id)
);