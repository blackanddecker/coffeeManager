-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema shopmanager
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema shopmanager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shopmanager` DEFAULT CHARACTER SET utf8 ;
USE `shopmanager` ;

-- -----------------------------------------------------
-- Table `shopmanager`.`shop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopmanager`.`shop` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopmanager`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopmanager`.`employee` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `status` VARCHAR(45) NULL,
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_shop1_idx` (`shop_id` ASC),
  CONSTRAINT `fk_employee_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `shopmanager`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopmanager`.`orderinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopmanager`.`orderinfo` (
  `id` INT NOT NULL,
  `details` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopmanager`.`table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopmanager`.`table` (
  `id` INT NOT NULL,
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_table_shop1_idx` (`shop_id` ASC),
  CONSTRAINT `fk_table_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `shopmanager`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopmanager`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopmanager`.`order` (
  `id` INT NOT NULL,
  `price` FLOAT NULL,
  `date_order` DATETIME NULL,
  `status` VARCHAR(45) NULL,
  `date_paid` VARCHAR(45) NULL,
  `orderinfo_id` INT NOT NULL,
  `table_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_orderinfo1_idx` (`orderinfo_id` ASC),
  INDEX `fk_order_table1_idx` (`table_id` ASC),
  CONSTRAINT `fk_order_orderinfo1`
    FOREIGN KEY (`orderinfo_id`)
    REFERENCES `shopmanager`.`orderinfo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_table1`
    FOREIGN KEY (`table_id`)
    REFERENCES `shopmanager`.`table` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopmanager`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopmanager`.`product` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `price` FLOAT NULL,
  `details` VARCHAR(45) NULL,
  `orderinfo_id` INT NOT NULL,
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_orderinfo1_idx` (`orderinfo_id` ASC),
  INDEX `fk_product_shop1_idx` (`shop_id` ASC),
  CONSTRAINT `fk_product_orderinfo1`
    FOREIGN KEY (`orderinfo_id`)
    REFERENCES `shopmanager`.`orderinfo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `shopmanager`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
