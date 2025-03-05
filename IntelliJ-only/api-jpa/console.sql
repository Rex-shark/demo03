



-- N+1
SELECT * from parent;
SELECT * from child;
SELECT * from user_info;
SELECT * from course;
SELECT * from child_course;


INSERT INTO jpa_demo.parent (id, created_at, created_user_id, status, update_user_id, updated_at, name) VALUES (1, '2025-02-26 15:06:28', 1, 1, null, '2025-02-26 15:06:28', 'P1');
INSERT INTO jpa_demo.parent (id, created_at, created_user_id, status, update_user_id, updated_at, name) VALUES (2, '2025-02-26 15:06:42', 1, 1, null, '2025-02-26 15:06:42', 'P2');
INSERT INTO jpa_demo.parent (id, created_at, created_user_id, status, update_user_id, updated_at, name) VALUES (3, '2025-02-26 15:06:42', 1, 1, null, '2025-02-26 15:06:42', 'P3');

INSERT INTO jpa_demo.child (id, created_at, created_user_id, status, update_user_id, updated_at, name, parent_id) VALUES (1, '2025-02-26 15:07:22', 1, 1, null, '2025-02-26 15:07:22', 'C1', 1);
INSERT INTO jpa_demo.child (id, created_at, created_user_id, status, update_user_id, updated_at, name, parent_id) VALUES (2, '2025-02-26 15:07:22', 1, 1, null, '2025-02-26 15:07:22', 'C2', 1);
INSERT INTO jpa_demo.child (id, created_at, created_user_id, status, update_user_id, updated_at, name, parent_id) VALUES (3, '2025-02-26 15:07:22', 1, 1, null, '2025-02-26 15:07:22', 'C3', 2);

INSERT INTO jpa_demo.user_info (id, phone_number, child_id, parent_id) VALUES (1, '0922', 1, null);
INSERT INTO jpa_demo.user_info (id, phone_number, child_id, parent_id) VALUES (2, '4257', 2, null);
INSERT INTO jpa_demo.user_info (id, phone_number, child_id, parent_id) VALUES (3, '5275', 3, null);
INSERT INTO jpa_demo.user_info (id, phone_number, child_id, parent_id) VALUES (4, '0539', null, 1);
INSERT INTO jpa_demo.user_info (id, phone_number, child_id, parent_id) VALUES (5, '8870', null, 2);
INSERT INTO jpa_demo.user_info (id, phone_number, child_id, parent_id) VALUES (6, '7896', null, 3);

INSERT INTO jpa_demo.course (id, created_at, created_user_id, status, update_user_id, updated_at, title) VALUES (1, '2025-02-27 09:03:53', 1, 1, null, '2025-02-27 09:03:53', '國文');
INSERT INTO jpa_demo.course (id, created_at, created_user_id, status, update_user_id, updated_at, title) VALUES (2, '2025-02-27 09:04:13', 1, 1, null, '2025-02-27 09:04:13', '數學');
INSERT INTO jpa_demo.course (id, created_at, created_user_id, status, update_user_id, updated_at, title) VALUES (3, '2025-02-27 09:04:13', 1, 1, null, '2025-02-27 09:04:13', '英文');

INSERT INTO jpa_demo.child_course (child_id, course_id) VALUES (1, 1);
INSERT INTO jpa_demo.child_course (child_id, course_id) VALUES (1, 2);
INSERT INTO jpa_demo.child_course (child_id, course_id) VALUES (1, 3);
INSERT INTO jpa_demo.child_course (child_id, course_id) VALUES (2, 1);
INSERT INTO jpa_demo.child_course (child_id, course_id) VALUES (3, 1);
INSERT INTO jpa_demo.child_course (child_id, course_id) VALUES (3, 2);
