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
    gift_id BIGINT NOT NULL,
    tag_id         BIGINT NOT NULL,
    FOREIGN KEY (gift_id) REFERENCES gift_certificate (id),
    FOREIGN KEY (tag_id) REFERENCES tags (ID)
);


