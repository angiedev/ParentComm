# DB Schema Creation Script for Parent Comm Application

CREATE TABLE Teacher(teacher_id BIGINT NOT NULL auto_increment,
	prefix VARCHAR(4),
	first_name VARCHAR(40),
	last_name VARCHAR(40),
	PRIMARY KEY(teacher_id)
) ENGINE=INNODB;
	

CREATE TABLE Class(class_id BIGINT NOT NULL auto_increment,
	school_id VARCHAR(12),
	grade VARCHAR(2) NOT NULL,
	teacher_id BIGINT,
	PRIMARY KEY (class_id),
	UNIQUE INDEX (school_id),
	FOREIGN KEY (teacher_id) REFERENCES Teacher(teacher_id)
) ENGINE=INNODB;

