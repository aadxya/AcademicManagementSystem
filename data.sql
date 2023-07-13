--
DROP TABLE IF EXISTS acadSess;
CREATE TABLE acadSess (
  currYear text,
  currSem text
) ;
INSERT INTO acadSess VALUES ('2020','1');

DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
  admin_username text DEFAULT NULL,
  admin_password text DEFAULT NULL,
  PRIMARY KEY(admin_username)
) ;
INSERT INTO admin VALUES ('admin','admin');



DROP TABLE IF EXISTS courseCatalog;
CREATE TABLE courseCatalog (
  course_id text NOT NULL,
  course_name text NOT NULL,
  credits int NOT NULL,
  ltp text NOT NULL,
  prereq text DEFAULT NULL,
  PRIMARY KEY (course_id)
) ;
-- INSERT INTO courseCatalog VALUES ('cs101','Discrete Mathematics','3','2-2-2',NULL);
-- INSERT INTO courseCatalog VALUES ('ge103','Intro to programming','3','2-2-2',NULL);
-- INSERT INTO courseCatalog VALUES ('cs201','DSA','3','2-2-2','cs101');
-- INSERT INTO courseCatalog VALUES ('cs302','Algo','3','2-2-2','cs201');
INSERT INTO courseCatalog VALUES ('cs201','DSA','3','2-2-2',NULL);
INSERT INTO courseCatalog VALUES ('cs202','PPaP','3','2-2-2',NULL);
INSERT INTO courseCatalog VALUES ('cs301','DB','3','2-2-2','cs202');
INSERT INTO courseCatalog VALUES ('cs302','Algo','3','2-2-2','cs201');


DROP TABLE IF EXISTS courseFloat;
CREATE TABLE courseFloat (
  course_id text NOT NULL,
  course_name text DEFAULT NULL,
  credits text NOT NULL,
  ltp text NOT NULL,
  instructor_id text NOT NULL,
  instructor_name text NOT NULL,
  prereq text DEFAULT NULL,
  cgreq text NOT NULL,
  currYear text NOT NULL,
  sem text NOT NULL,
  PRIMARY KEY (course_id)
) ;

-- DROP TABLE IF EXISTS st2020CSB1039;
-- CREATE TABLE st2020CSB1039(
--   course_id text NOT NULL,
--   course_name text NOT NULL,
--   course_type text NOT NULL,
--   credits text NOT NULL,
--   ltp text NOT NULL,
--   grade text NOT NULL,
--   currYear text NOT NULL,
--   sem text NOT NULL,  
--   PRIMARY KEY (course_id)
-- );

-- DROP TABLE IF EXISTS cs20120201;
-- CREATE TABLE cs20120201(
--   student_id text,
--   grade text,
--   PRIMARY KEY (student_id)
-- );



DROP TABLE IF EXISTS instructor;
CREATE TABLE instructor (
  instructor_id text DEFAULT NULL,
  instructor_pass text DEFAULT NULL,
  instructor_name text DEFAULT NULL,
  department text DEFAULT NULL,
  phone_no text DEFAULT NULL,
  PRIMARY KEY(instructor_id)
) ;

DROP TABLE IF EXISTS student;
CREATE TABLE student (
  student_name text DEFAULT NULL,
  student_id text DEFAULT NULL,
  student_pass text DEFAULT NULL,
  phone_no text DEFAULT NULL,
  department text DEFAULT NULL,
  batch text DEFAULT NULL,
  primary key (student_id)
) ;

DROP TABLE IF EXISTS batch;
CREATE TABLE batch (
  year text DEFAULT NULL,
  pc text DEFAULT NULL,
  pe text DEFAULT NULL,
  semI text,
  semII text,
  primary key(year)
) ;

DROP TABLE IF EXISTS ug_cirriculum;
CREATE TABLE ug_cirriculum (
  batch text,
  course_id text,
  course_type text,
  primary key(batch,course_id)
) ;
