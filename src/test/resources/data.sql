insert into person(`id`,`name`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (1,'martin','A',1991,8,15);
insert into person(`id`,`name`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (2,'cathy','O',1992,8,31);
insert into person(`id`,`name`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (3,'cindy','AB',1999,9,20);
insert into person(`id`,`name`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (4,'emma','B',2004,3,1);
insert into person(`id`,`name`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`) values (5,'jacob','A',2001,2,4);
insert into person(`id`,`name`,`blood_type`,`year_of_birthday`,`month_of_birthday`,`day_of_birthday`,`job`,`hobby`,`address`)
values (6,'tony','A',1991,7,10,'officer','reading','서울');
insert into person(`id`,`name`,`deleted`) values (7,'andrew',true);

insert into block(`id`,`name`) values (1,'emma');
insert into block(`id`,`name`) values (2,'jacob');

update person set block_id = 1 where id = 4;
update person set block_id = 2 where id = 5;