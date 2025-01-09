select * from order_demo01;

ALTER TABLE user_base AUTO_INCREMENT = 1;
ALTER TABLE sys_role AUTO_INCREMENT = 1;
ALTER TABLE Sys_User_Role AUTO_INCREMENT = 1;
ALTER TABLE Sys_Role_Menu AUTO_INCREMENT = 1;
ALTER TABLE Sys_Menu AUTO_INCREMENT = 1;

select * from user_base;
select * from sys_role;
select * from Sys_User_Role;
select * from Sys_Role_Menu;
select * from Sys_Menu;

DROP TABLE user_base;
DROP TABLE sys_role;
DROP TABLE Sys_User_Role;
DROP TABLE Sys_Role_Menu;
DROP TABLE Sys_Menu;

SELECT ub.*, sm.id ,sm.parent_id ,sr.nid
FROM User_Base ub
         LEFT JOIN Sys_User_Role sur ON ub.id = sur.user_Id
         LEFT JOIN Sys_Role sr ON sr.id = sur.role_id
         LEFT JOIN Sys_Role_Menu srm ON srm.sys_Role_Id = sr.id
         LEFT JOIN Sys_Menu sm ON sm.id = srm.sys_Menu_Id


select * from order_demo;-- order1 67586bd9-9afc-4f47-8c78-d31a5876cd79