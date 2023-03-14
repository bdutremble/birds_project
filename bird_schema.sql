
DROP TABLE IF EXISTS bird_habitat;
DROP TABLE IF EXISTS diet;
DROP TABLE IF EXISTS observation;
DROP TABLE IF EXISTS bird;
DROP TABLE IF EXISTS food;
DROP TABLE IF EXISTS habitat;

CREATE TABLE habitat (
  habitat_pk int unsigned NOT NULL AUTO_INCREMENT,
  habitat_type set('AQUATIC', 'WOODLAND', 'SCRUB-SHRUB', 'OPEN') NOT NULL,
  PRIMARY KEY (habitat_pk)
);

CREATE TABLE food (
  food_pk int unsigned NOT NULL AUTO_INCREMENT,
  food_id set('FRUIT', 'SEED', 'INVERTEBRATES','RODENTS', 'FISH') NOT NULL,
  PRIMARY KEY (food_pk)
);

CREATE TABLE bird (
  bird_pk int unsigned NOT NULL AUTO_INCREMENT,
  common_name varchar(40) NOT NULL,
  scientific_name varchar(80),
  beak_color_id varchar(30),
  head_color_id varchar(30),
  torso_color_id varchar(30) NOT NULL,
  wing_color_id varchar(30),
  bird_size_id enum('SMALL', 'MEDIUM', 'LARGE') NOT NULL,
  wingspan_cm int unsigned NOT NULL,
  nest_type enum('GROUND', 'PLATFORM', 'CAVITY', 'BOWL', 'WILD') NOT NULL,
  PRIMARY KEY (bird_pk)
);

CREATE TABLE observation (
  observation_pk int unsigned NOT NULL AUTO_INCREMENT,
  bird_fk int unsigned NOT NULL,
  observation_time TIMESTAMP NOT NULL,
  observation_location varchar(30) NOT NULL,
  PRIMARY KEY (observation_pk),
  FOREIGN KEY (bird_fk) REFERENCES bird (bird_pk) ON DELETE CASCADE
);

CREATE TABLE diet (
  bird_id int unsigned NOT NULL,
  food_id int unsigned NOT NULL,
  CONSTRAINT diet_pk PRIMARY KEY (bird_id, food_id),
  CONSTRAINT diet_bird_fk
      FOREIGN KEY (bird_id) REFERENCES bird (bird_pk),
  CONSTRAINT diet_food_fk 
      FOREIGN KEY (food_id) REFERENCES food (food_pk) ON DELETE CASCADE
);

CREATE TABLE bird_habitat (
  bird_id int unsigned NOT NULL,
  habitat_id int unsigned NOT NULL,
  CONSTRAINT bird_habitat_pk PRIMARY KEY (bird_id, habitat_id),
  CONSTRAINT bird_habitat_bird_fk
      FOREIGN KEY (bird_id) REFERENCES bird (bird_pk),
  CONSTRAINT bird_habitat_habitat_fk 
      FOREIGN KEY (habitat_id) REFERENCES habitat (habitat_pk) ON DELETE CASCADE
);