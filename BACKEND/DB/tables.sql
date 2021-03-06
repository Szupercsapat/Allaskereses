CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(60) DEFAULT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(24) NOT NULL,
   `email` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
     activated tinyint(1),
    CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`),
   UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_activation` (
 `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `activation_string` varchar(20) DEFAULT NULL,
  CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expiration_date TIMESTAMP,
  PRIMARY KEY (`ID`),
  CONSTRAINT `user_activation_for` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `USER_ID` int(11) DEFAULT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  KEY `USER_ID` (`USER_ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `role_user_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`id`),
  CONSTRAINT `role_user_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `job_offerer` ( /*with user table - one to one*/ /*with job_offerer_job_categry many to many*/
  `ID` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) not null,
  `first_name` varchar(60) not NULL,
  `last_name` varchar(60) not NULL,
  `about_me` TEXT DEFAULT NULL,
  `profile_image` BLOB DEFAULT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  CONSTRAINT `user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `job_seeker` ( /*with user table - one to one*/ /*with job_seeker_job_categry many to many*/
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) not null,
  `first_name` varchar(60) not NULL,
  `last_name` varchar(60) not NULL,
  `about_me` TEXT DEFAULT NULL,
  `profile_image` BLOB DEFAULT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  CONSTRAINT `user_id_foreig` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `job_category` ( 
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) DEFAULT NULL,
  parent_id int(11) ,
  `about` varchar(255) DEFAULT NULL,
   active tinyint(1),
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `job_offerer_job_category` (
	`offerer_id` int(11) NOT NULL,
	`category_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `seeker_id_foreign` FOREIGN KEY (`offerer_id`) REFERENCES `job_offerer` (`id`),
  CONSTRAINT `category_id_foreign` FOREIGN KEY (`category_id`) REFERENCES `job_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `job_seeker_job_category` (
	`seeker_id` int(11) NOT NULL,
	`category_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `seeker_id_foreig` FOREIGN KEY (`seeker_id`) REFERENCES `job_seeker` (`id`),
  CONSTRAINT `category_id_foreig` FOREIGN KEY (`category_id`) REFERENCES `job_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seeker_id` int(11) DEFAULT NULL,
  `from_year` int DEFAULT NULL,
  `to_year` int DEFAULT NULL,
  `name` varchar(200) default null,
    CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `seeker_id` (`seeker_id`),
  CONSTRAINT `job_seeker_ibfk_1` FOREIGN KEY (`seeker_id`) REFERENCES `job_seeker` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `work_place` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seeker_id` int(11) DEFAULT NULL,
  `from_year` int DEFAULT NULL,
  `to_year` int DEFAULT NULL,
  `name` varchar(200) default null,
    CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `seeker_id` (`seeker_id`),
  CONSTRAINT `job_seeker_ibfk_2` FOREIGN KEY (`seeker_id`) REFERENCES `job_seeker` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `offerer_id` int(11) DEFAULT NULL,
  `name` varchar(200) default null,
  `description` text default null,
    CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `offerer_id` (`offerer_id`),
  FOREIGN KEY (`offerer_id`) REFERENCES `job_offerer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `job_job_category` (
	`job_id` int(11) NOT NULL,
	`category_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `job_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


insert into job_category(job_name,parent_id,about,active) values('Vendéglátás',null,'Vendéglátás munka',0);
insert into job_category(job_name,parent_id,about,active) values('Hotel',3,'Hotel munka',1);

insert into job_category(job_name,parent_id,about,active) values('Ügyfélszolgálat',null,'Ügyfélszolgálati munka',0);
insert into job_category(job_name,parent_id,about,active) values('Vevőszolgálat',5,'Vevőszolgálat munka',1);

insert into job_category(job_name,parent_id,about,active) values('IT üzemeltetés',null,'IT üzemeltetés munka',0);
insert into job_category(job_name,parent_id,about,active) values('Telekommunikáció',7,'Telekommunikáció munka',1);

insert into job_category(job_name,parent_id,about,active) values('IT programozás',null,'IT programozási munka',0);
insert into job_category(job_name,parent_id,about,active) values('Fejlesztés',9,'Fejlesztés munka',1);

insert into job_category(job_name,parent_id,about,active) values('Szállítás',null,'Szállítási munka',0);
insert into job_category(job_name,parent_id,about,active) values('Beszerzés',11,'Beszerzési munka',1);

insert into job_category(job_name,parent_id,about,active) values('HR',null,'HR',0);
insert into job_category(job_name,parent_id,about,active) values('Munkaügy',13,'Munkaügyi munka',1);

insert into job_category(job_name,parent_id,about,active) values('Fizikai',null,'Fizikai munka',0);
insert into job_category(job_name,parent_id,about,active) values('Segéd',15,'Segéd munka',1);

insert into job_category(job_name,parent_id,about,active) values('Gyártás',null,'Gyártás munka',0);
insert into job_category(job_name,parent_id,about,active) values('Termelés',17,'Termelési munka',1);

insert into job_category(job_name,parent_id,about,active) values('Értékesítés',null,'Értékesítési munka',0);
insert into job_category(job_name,parent_id,about,active) values('Kereskedelem',19,'Kereskedelemi munka',1);

insert into job_category(job_name,parent_id,about,active) values('Oktatás',null,'Oktatási munka',0);
insert into job_category(job_name,parent_id,about,active) values('Kutatás',21,'Kutatási munka',1);

insert into role(name) values('ROLE_ADMIN');
insert into role(name) values('ROLE_USER');

select * from role;
select * from user;
select * from user_role;
select * from job_offerer;
select * from job_seeker;
select * from job_category;
select * from user_activation;
select * from job_offerer_job_category;
select * from job_seeker_job_category;
select * from school;
select * from work_place;
select * from job;
select * from job_job_category;

use rft;

drop table job_offerer_job_category;
drop table job_seeker_job_category;
drop table job_job_category;
drop table job;
drop table job_category;
drop table job_offerer;
drop table school;
drop table work_place;
drop table job_seeker;
drop table user_role;
drop table user_activation;
drop table role;
drop table user;

select * from job_category;


select j.* from job j inner join job_job_category jc on jc.job_id = j.id inner join job_category c on c.id = jc.category_id
where c.id in (3,5);




