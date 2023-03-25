
DROP TABLE IF EXISTS bird_observation;
DROP TABLE IF EXISTS bird_habitat;
DROP TABLE IF EXISTS bird_coloration;
DROP TABLE IF EXISTS observation;
DROP TABLE IF EXISTS bird;
DROP TABLE IF EXISTS habitat;
DROP TABLE IF EXISTS coloration;

CREATE TABLE habitat (
  habitat_id int unsigned NOT NULL AUTO_INCREMENT,
  habitat_type varchar(30) NOT NULL,
  habitat_description text,
  PRIMARY KEY (habitat_id)
);

CREATE TABLE coloration (
  coloration_id int unsigned NOT NULL AUTO_INCREMENT,
  beak_color_id varchar(30),
  head_color_id varchar(30),
  torso_color_id varchar(30),
  wing_color_id varchar(30),
  PRIMARY KEY (coloration_id)
);

CREATE TABLE observation (
  observation_id int unsigned NOT NULL AUTO_INCREMENT,
  observation_time varchar(40) NOT NULL,
  observation_location varchar(30) NOT NULL,
  PRIMARY KEY (observation_id)
);

CREATE TABLE bird (
  bird_id int unsigned NOT NULL AUTO_INCREMENT,
  common_name varchar(40) NOT NULL,
  scientific_name varchar(80),
  bird_sex_id varchar(10),
  PRIMARY KEY (bird_id)
);

CREATE TABLE bird_habitat (
  bird_id int unsigned NOT NULL,
  habitat_id int unsigned NOT NULL,
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE,
  FOREIGN KEY (habitat_id) REFERENCES habitat (habitat_id) ON DELETE CASCADE,
  UNIQUE KEY (bird_id, habitat_id)
);

CREATE TABLE bird_coloration (
  bird_id int unsigned NOT NULL,
  coloration_id int unsigned NOT NULL,
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE,
  FOREIGN KEY (coloration_id) REFERENCES coloration (coloration_id) ON DELETE CASCADE,
  UNIQUE KEY (bird_id, coloration_id)
);

CREATE TABLE bird_observation (
  bird_id int unsigned NOT NULL,
  observation_id int unsigned NOT NULL,
  FOREIGN KEY (bird_id) REFERENCES bird (bird_id) ON DELETE CASCADE,
  FOREIGN KEY (observation_id) REFERENCES observation (observation_id) ON DELETE CASCADE,
  UNIQUE KEY (bird_id, observation_id)
);