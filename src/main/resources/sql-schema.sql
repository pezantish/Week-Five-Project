drop schema if exists `ims`;
CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims`;


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

INSERT INTO `ims`.`customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');
INSERT INTO `ims`.`items` (`name`, `price`) VALUES ('cheese', 2.0);
INSERT INTO `ims`.`orders` (`customer_id`) VALUES (3);
INSERT INTO `ims`.`order_items` (`order_id`, `item_id`) VALUES (1, 2);