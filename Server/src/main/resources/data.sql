insert into user (email, username, password, uuid) values ('ian@foo.com', 'ian','emacs', '7bdba0fe-fe95-4b1c-8247-f2479ee6e380');
insert into user (email, username, password, uuid) values ('sandy@foo.com','sandy','vim','2c77dafe-1545-432f-b5b1-3a0011cf7036');
insert into user (email, username, password, uuid) values ('vik@foo.com','vik','vscode','97489bce-1c85-4ff2-b457-ba53589d12cc');

insert into task (uuid, user, name, description) values ('e187ecea-167c-4feb-a13a-eb34949bb400', '7bdba0fe-fe95-4b1c-8247-f2479ee6e380', 'Server', 'Create server and pull request');

insert into entry (uuid, task, name, time, addedon, startedon, endedon) values ('42cca36b-cbee-4e70-9b87-b01935a3f7c7', 'e187ecea-167c-4feb-a13a-eb34949bb400', 'Create model classes.', 60, '2020-12-02 12:34:45', '2020-12-02 12:35:44', '2020-12-02 13:35:44');
