insert into user (email, username, password, uuid) values ('ian@foo.com', 'ian','emacs', '7bdba0fe-fe95-4b1c-8247-f2479ee6e380');
insert into user (email, username, password, uuid) values ('sandy@foo.com','sandy','vim','2c77dafe-1545-432f-b5b1-3a0011cf7036');
insert into user (email, username, password, uuid) values ('vik@foo.com','vik','vscode','97489bce-1c85-4ff2-b457-ba53589d12cc');

insert into note (uuid, title, body, reminder, created, modified) values ('e187ecea-167c-4feb-a13a-eb34949bb400', 'Foo', 'Foo body.', null, '2018-11-04 12:34:45', '2018-11-05 13:42:44');

insert into collaborator (id, user, note) values (1, '7bdba0fe-fe95-4b1c-8247-f2479ee6e380', 'e187ecea-167c-4feb-a13a-eb34949bb400');

