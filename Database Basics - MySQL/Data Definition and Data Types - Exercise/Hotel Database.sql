
CREATE TABLE `employees`
(
    `id`         INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name`  VARCHAR(50) NOT NULL,
    `title`      VARCHAR(200),
    `notes`      TEXT
);

CREATE TABLE `customers`
(
    `account_number`   INT PRIMARY KEY AUTO_INCREMENT,
    `first_name`       VARCHAR(50)  NOT NULL,
    `last_name`        VARCHAR(50)  NOT NULL,
    `phone_number`     INT UNSIGNED NOT NULL,
    `emergency_name`   VARCHAR(50),
    `emergency_number` INT UNSIGNED NOT NULL,
    `notes`            TEXT

);

CREATE TABLE `room_status`
(
    `room_status` BOOLEAN NOT NULL,
    `notes`       TEXT
);

CREATE TABLE `room_types`
(
    `room_type` VARCHAR(50) NOT NULL,
    `notes`     TEXT
);

CREATE TABLE `bed_types`
(
    `bed_type` VARCHAR(50) NOT NULL,
    `notes`    TEXT
);

CREATE TABLE `rooms`
(
    `room_number` INT AUTO_INCREMENT PRIMARY KEY,
    `room_type`   INT         NOT NULL,
    `bed_type`    INT         NOT NULL,
    `rate`        INT         NOT NULL,
    `room_status` VARCHAR(50) NOT NULL,
    `notes`       TEXT
);

CREATE TABLE `payments`
(
    `id`                  INT AUTO_INCREMENT PRIMARY KEY,
    `employee_id`         INT      NOT NULL,
    `payment_date`        DATETIME NOT NULL,
    `account_number`      INT      NOT NULL,
    `first_date_occupied` DATE     NOT NULL,
    `last_date_occupied`  DATE     NOT NULL,
    `total_days`          INT      NOT NULL,
    `amount_charged`      DECIMAL  NOT NULL,
    `tax_rate`            INT      NOT NULL,
    `tax_amount`          DECIMAL  NOT NULL,
    `payment_total`       DECIMAL  NOT NULL,
    `notes`               TEXT

);

CREATE TABLE `occupancies`
(
    `id`             INT AUTO_INCREMENT PRIMARY KEY,
    `employee_id`    INT     NOT NULL,
    `date_occupied`  DATE     NOT NULL,
    `account_number` INT     NOT NULL,
    `room_number`    INT     NOT NULL,
    `rate_applied`   DECIMAL NOT NULL,
    `phone_charge`   DECIMAL NOT NULL,
    `notes`          TEXT
);

INSERT INTO `employees`(first_name, last_name, title)
VALUES ('asd', 'asd', 'asd'),
       ('asd', 'asd', 'asd'),
       ('asd', 'asd', 'asd');

INSERT INTO `customers`(first_name, last_name, phone_number, emergency_name, emergency_number, notes)
VALUES ('ASD', 'asd', 088888888, 'asd', 08888888, NULL),
       ('ASD', 'asd', 088888888, 'asd', 08888888, NULL),
       ('ASD', 'asd', 088888888, 'asd', 08888888, NULL);

INSERT INTO `room_status` (room_status, notes)
VALUES (FALSE, NULL),
       (FALSE, NULL),
       (FALSE, NULL);

INSERT INTO `room_types` (room_type, notes)
VALUES ('ASD', NULL),
       ('ASD', NULL),
       ('ASD', NULL);

INSERT INTO `bed_types` (bed_type, notes)
VALUES ('ASD', NULL),
       ('ASD', NULL),
       ('ASD', NULL);

INSERT INTO `rooms` (room_type, bed_type, rate, room_status, notes)
VALUES (1, 2, 3, 'FREE', NULL),
       (1, 2, 3, 'FREE', NULL),
       (1, 2, 3, 'FREE', NULL);

INSERT INTO `payments` (employee_id, payment_date, account_number, first_date_occupied, last_date_occupied, total_days,
                        amount_charged, tax_rate, tax_amount, payment_total, notes)
VALUES (2, '2019-12-12 00:00:00', 1, '2019-12-20', '2019-12-20', 0, 0, 0, 0, 0, NULL),
       (2, '2019-12-12 00:00:00', 1, '2019-12-20', '2019-12-20', 0, 0, 0, 0, 0, NULL),
       (2, '2019-12-12 00:00:00', 1, '2019-12-20', '2019-12-20', 0, 0, 0, 0, 0, NULL);

INSERT INTO `occupancies` (employee_id, date_occupied, account_number, room_number, rate_applied, phone_charge, notes) VALUES
(1,'2019-12-12',1,2,10.00,10.00,NULL),
(1,'2019-12-12',1,2,10.00,10.00,NULL),
(1,'2019-12-12',1,2,10.00,10.00,NULL);