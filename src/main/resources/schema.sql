CREATE TABLE IF NOT EXISTS `movie`
(
    id long not null,
    title varchar(255) not null,
    year int not null

);

CREATE TABLE IF NOT EXISTS `person`
(
    id long not null,
    name varchar(255) not null

);


CREATE TABLE IF NOT EXISTS `crew`
(
    id long not null AUTO_INCREMENT,
    movie long not null,
    person long not null,
    role varchar not null
);