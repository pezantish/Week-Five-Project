drop schema if exists `ims`;
CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

DROP TABLE IF EXISTS orders;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`items` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) DEFAULT NULL,
    `price` DOUBLE DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`orders` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
	`customer_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`order_items` (
    `order_id` INT(11) NOT NULL,
	`item_id` INT(11) NOT NULL,
    PRIMARY KEY (`order_id`, `item_id`)
);

select * from orders