insert into movie (id, title, year) VALUES (2147483640, 'Star Wars', 1977 );
insert into movie (id, title, year) VALUES (1147483640, 'Citizen Kane', 1941 );
insert into movie (id, title, year) VALUES (1147483650, 'F for Fake', 1973 );
insert into movie (id, title, year) VALUES (2147483647, 'Empire Strikes Back', 1980 );
insert into movie (id, title, year) VALUES (2147483650, 'Return of the Jedi', 1983 );

insert into movie (id, title, year) VALUES (2147483651, 'The Blues Brothers', 1980 );

insert into movie (id, title, year) VALUES (2147483645, 'Corvette Summer', 1978 );
insert into movie (id, title, year) VALUES (2147483655, 'The Guyver', 1991 );
insert into movie (id, title, year) VALUES (2147483670, 'Comic Book: The Movie', 2004 );

insert into person (id, name) VALUES (1, 'Mark Hamill' );
insert into person (id, name) VALUES (2, 'Orson Welles' );
insert into person (id, name) VALUES (3, 'Harrison Ford' );
insert into person (id, name) VALUES (4, 'Carrie Fisher' );
insert into person (id, name) VALUES (5, 'John Belushi');


insert into crew (movie, person, role) VALUES (1147483650, 2, 'CAST' );
insert into crew (movie, person, role) VALUES (1147483650, 2, 'DIRECTOR' );
insert into crew (movie, person, role) VALUES (1147483650, 2, 'WRITER' );

insert into crew (movie, person, role) VALUES (1147483640, 2, 'CAST' );
insert into crew (movie, person, role) VALUES (1147483640, 2, 'DIRECTOR' );
insert into crew (movie, person, role) VALUES (1147483640, 2, 'WRITER' );


insert into crew (movie, person, role) VALUES (2147483640, 1, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483647, 1, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483650, 1, 'CAST' );

insert into crew (movie, person, role) VALUES (2147483645, 1, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483655, 1, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483670, 1, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483670, 1, 'DIRECTOR' );
insert into crew (movie, person, role) VALUES (2147483670, 1, 'WRITER' );

insert into crew (movie, person, role) VALUES (2147483640, 3, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483647, 3, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483650, 3, 'CAST' );

insert into crew (movie, person, role) VALUES (2147483640, 4, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483647, 4, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483650, 4, 'CAST' );

insert into crew (movie, person, role) VALUES (2147483651, 4, 'CAST' );
insert into crew (movie, person, role) VALUES (2147483651, 5, 'CAST' );



