create table tags
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

create table gift_certificate
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             varchar(45)  NOT NULL UNIQUE,
    description      VARCHAR(80) NOT NULL,
    price            DOUBLE NOT NULL,
    create_date      TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP NOT NULL,
    duration         INT NOT NULL
);

create table tag_certificate_associate
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    gift_id BIGINT NOT NULL,
    tag_id         BIGINT NOT NULL,
    FOREIGN KEY (gift_id) REFERENCES gift_certificate (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('certificate 1', 'description 1', 1.1, '2021-10-08 11:11:11', '2021-01-01 01:22:11', 1),
       ('certificate 2', 'description 2', 2.2, '2021-10-08 11:11:11', '2021-01-01 01:22:11', 2),
       ('certificate 3', 'description 3', 3.3, '2021-10-08 11:11:11', '2021-01-01 01:22:11', 3);

INSERT INTO tags ("NAME") VALUES ('IT'), ('HR'), ('Java'), ('Epam');

INSERT INTO tag_certificate_associate (gift_id, tag_id) VALUES (1, 1), (1, 3), (2, 2);

