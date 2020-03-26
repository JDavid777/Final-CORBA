CREATE TABLE `DB_Alerts`.`patient` ( `roomID` INT(255) NOT NULL , `name` VARCHAR(100) NOT NULL , `lastname` VARCHAR(100) NOT NULL , `birthday` VARCHAR(100) NOT NULL , PRIMARY KEY (`roomID`)) ENGINE = InnoDB;

CREATE TABLE `DB_Alerts`.`alerts` ( `roomIDAlerta` INT NOT NULL , `date` VARCHAR(100) NOT NULL , `hour` VARCHAR(100) NOT NULL , `score` INT NOT NULL , PRIMARY KEY (`roomIDAlerta`)) ENGINE = InnoDB;
