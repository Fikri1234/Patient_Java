--liquibase formatted sql

-- changeset fikri:1

CREATE SCHEMA IF NOT EXISTS patient_db;

CREATE TABLE `patient` (
  `pid` bigint NOT NULL AUTO_INCREMENT,
  `firstname` varchar(250) DEFAULT NULL,
  `lastname` varchar(250) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` ENUM('MALE', 'FEMALE') DEFAULT NULL,
  `patient_address_id` bigint DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pid`)
);

CREATE TABLE `patient_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(250) DEFAULT NULL,
  `suburb` varchar(250) DEFAULT NULL,
  `state` varchar(250) DEFAULT NULL,
  `postcode` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
);