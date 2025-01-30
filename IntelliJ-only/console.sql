select * from order_demo;

ALTER TABLE user_base AUTO_INCREMENT = 1;
ALTER TABLE sys_role AUTO_INCREMENT = 1;
ALTER TABLE Sys_User_Role AUTO_INCREMENT = 1;
ALTER TABLE Sys_Role_Menu AUTO_INCREMENT = 1;
ALTER TABLE Sys_Menu AUTO_INCREMENT = 1;

select * from user_base;
select * from sys_role;
select * from Sys_User_Role;
select * from Sys_Menu;
select * from Sys_Role_Menu;

select * from Sys_User_Role where user_id = 7;
select * from Sys_Menu where parent_id = 0;


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
         LEFT JOIN Sys_Menu sm ON sm.id = srm.sys_Menu_Id;


select * from order_demo;-- order1 67586bd9-9afc-4f47-8c78-d31a5876cd79
select * from product_demo;

-- 找菜單 , USER 只能看到 我的訂單 與其底下的

select * from Sys_Menu where
                           parent_id = ( select id from Sys_Menu where nid = '我的訂單'  )
                            or nid = '我的訂單' ;
;

select
    srm1_0.id,
    srm1_0.remark,
    srm1_0.sys_menu_id,
    srm1_0.sys_role_id
from
    sys_role_menu srm1_0
where
    srm1_0.sys_role_id=5