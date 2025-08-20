USE [LearningZ]
GO

INSERT INTO [vip_package] ([package_name], [duration],discounted_price, [price],[status])
VALUES
(N'6 months', 6,450000, 500000,1),
(N'1 year', 12,0, 900000,1),
(N'2 years', 24,0, 1600000,1);


insert into system_setting(setting_name, setting_value)
values
    ('Expired time (second)', 180),
    ('Max ad show', 5),
    ('Max chapter in course', 10),
    ('Max lesson in chapter', 10),
    ('Max quiz in lesson', 3),
    ('Max question in quiz', 50),
    ('Max times take a quiz',3),
    ('Min score to pass',8);

--Users (id 1-40 là student, 41-46 là teacher)
INSERT INTO [dbo].[users] (created_at, phone_num, avt_url, email, [password], [role], user_status, username)
VALUES

-- VIP STUDENT
('2025-01-01 08:30:00', '0987654321', '/image/AvartaDefault.jpg', 'emilynguyen@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'EmilyNguyen'),

('2025-01-02 09:15:00', '0912345678', '/image/AvartaDefault.jpg', 'michaeltran@yahoo.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'MichaelTran'),

('2025-01-03 14:20:00', '0971122334', '/image/AvartaDefault.jpg', 'davidpham@hotmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'BANNED', 'DavidPham'),

('2025-01-04 10:50:00', '0969988776', '/image/AvartaDefault.jpg', 'jessicale@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'JessicaLe'),

('2025-01-05 11:30:00', '0935671234', '/image/AvartaDefault.jpg', 'williamdinh@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'BANNED', 'WilliamDinh'),

('2025-01-06 08:00:00', '0983344556', '/image/AvartaDefault.jpg', 'hannahbui@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'HannahBui'),

('2025-01-07 13:45:00', '0947888999', '/image/AvartaDefault.jpg', 'ethannguyen@yahoo.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'EthanNguyen'),

('2025-03-08 15:10:00', '0922334455', '/image/AvartaDefault.jpg', 'sophiatran@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'SophiaTran'),

('2025-03-09 17:20:00', '0977896543', '/image/AvartaDefault.jpg', 'danielphan@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'DanielPhan'),

('2025-03-10 19:30:00', '0954321987', '/image/AvartaDefault.jpg', 'oliviahoang@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'OliviaHoang'),

('2025-03-11 10:00:00', '0909876543', '/image/AvartaDefault.jpg', 'ryanvo@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'RyanVo'),

('2025-02-12 12:30:00', '0912233445', '/image/AvartaDefault.jpg', 'avadang@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'BANNED', 'AvaDang'),

('2025-02-13 14:45:00', '0977885544', '/image/AvartaDefault.jpg', 'nathanbach@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'NathanBach'),

('2025-02-14 16:10:00', '0923344556', '/image/AvartaDefault.jpg', 'miahuynh@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'MiaHuynh'),

('2025-03-15 18:25:00', '0987651234', '/image/AvartaDefault.jpg', 'alexanderngo@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'BANNED', 'AlexanderNgo'),

('2025-03-16 20:40:00', '0965432198', '/image/AvartaDefault.jpg', 'sophiachu@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'SophiaChu'),

('2025-03-17 22:55:00', '0956677889', '/image/AvartaDefault.jpg', 'benjaminha@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'BenjaminHa'),

('2024-01-18 09:05:00', '0945566778', '/image/AvartaDefault.jpg', 'charlottedo@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'CharlotteDo'),

('2024-02-19 11:15:00', '0934455667', '/image/AvartaDefault.jpg', 'lucasvu@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'LucasVu'),

('2024-02-20 13:25:00', '0912233445', '/image/AvartaDefault.jpg', 'amelialy@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'VIP_STUDENT', 'ACTIVE', 'AmeliaLy'),

 -- STUDENT
('2024-03-21 08:30:00', '0981111222', '/image/AvartaDefault.jpg', 'truongthuankhiet@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'TruongNT'),

('2024-03-22 09:15:00', '0912222333', '/image/AvartaDefault.jpg', 'madisontran@yahoo.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'MadisonTran'),

('2024-03-23 14:20:00', '0973333444', '/image/AvartaDefault.jpg', 'tylerpham@hotmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'BANNED', 'TylerPham'),

('2024-03-24 10:50:00', '0964444555', '/image/AvartaDefault.jpg', 'averyle@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'AveryLe'),

('2024-03-25 11:30:00', '0935555666', '/image/AvartaDefault.jpg', 'jacksondinh@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'BANNED', 'JacksonDinh'),

('2025-03-26 09:00:00', '0986666777', '/image/AvartaDefault.jpg', 'gracebui@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'GraceBui'),

('2025-03-27 13:45:00', '0947777888', '/image/AvartaDefault.jpg', 'loganvu@yahoo.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'LoganVu'),

('2025-02-28 15:10:00', '0928888999', '/image/AvartaDefault.jpg', 'scarletttran@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'ScarlettTran'),

('2025-01-29 17:20:00', '0979999000', '/image/AvartaDefault.jpg', 'leohoang@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'LeoHoang'),

('2025-01-30 19:30:00', '0951111223', '/image/AvartaDefault.jpg', 'zoyanguyen@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'ZoyaNguyen'),

('2025-03-31 10:00:00', '0902222334', '/image/AvartaDefault.jpg', 'sebastianvo@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'SebastianVo'),

('2025-02-01 12:30:00', '0913333445', '/image/AvartaDefault.jpg', 'chloebach@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'BANNED', 'ChloeBach'),

('2025-02-02 14:45:00', '0974444556', '/image/AvartaDefault.jpg', 'aidendoan@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'AidenDoan'),

('2025-02-03 16:10:00', '0925555667', '/image/AvartaDefault.jpg', 'lilyhuynh@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'LilyHuynh'),

('2025-02-04 18:25:00', '0986666778', '/image/AvartaDefault.jpg', 'noahngo@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'BANNED', 'NoahNgo'),

('2025-01-05 20:40:00', '0967777889', '/image/AvartaDefault.jpg', 'brooklynchu@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'BrooklynChu'),

('2025-01-06 22:55:00', '0958888990', '/image/AvartaDefault.jpg', 'isaiahha@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'IsaiahHa'),

('2025-01-07 09:05:00', '0949999001', '/image/AvartaDefault.jpg', 'aurorado@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'AuroraDo'),

('2025-01-08 11:15:00', '0931111224', '/image/AvartaDefault.jpg', 'lukevu@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'LukeVu'),

('2025-01-09 13:25:00', '0912222335', '/image/AvartaDefault.jpg', 'violetly@gmail.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'STUDENT', 'ACTIVE', 'VioletLy'),

 -- TEACHER
('2025-01-01 08:30:00', '0981111222', '/image/AvartaDefault.jpg', 'danielnguyen@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'TEACHER', 'ACTIVE', 'DanielNguyen'),

('2025-01-01 09:15:00', '0912222333', '/image/AvartaDefault.jpg', 'emilytran@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'TEACHER', 'ACTIVE', 'EmilyTran'),

('2025-01-01 14:20:00', '0973333444', '/image/AvartaDefault.jpg', 'michaelpham@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'TEACHER', 'BANNED', 'MichaelPham'),

('2025-01-01 10:50:00', '0964444555', '/image/AvartaDefault.jpg', 'sophiale@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'TEACHER', 'ACTIVE', 'SophiaLe'),

('2025-01-01 11:30:00', '0935555666', '/image/AvartaDefault.jpg', 'jacobdinh@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'TEACHER', 'BANNED', 'JacobDinh'),

('2025-01-01 08:00:00', '0986666777', '/image/AvartaDefault.jpg', 'oliviabui@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'TEACHER', 'ACTIVE', 'OliviaBui'),

 -- ADMIN
 ('2025-01-01 08:00:00', '0986656777', '/image/AvartaDefault.jpg', 'chiktm@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'ADMIN', 'ACTIVE', 'Cheese'),
 ('2025-01-01 08:00:00', '0985666777', '/image/AvartaDefault.jpg', 'giapnh@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'ADMIN_STUDENT_MANAGER', 'ACTIVE', 'Giap'),
 ('2025-01-01 08:00:00', '0986665777', '/image/AvartaDefault.jpg', 'loclx@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'ADMIN_COURSE_MANAGER', 'ACTIVE', 'Loc'),

 -- Marketing Team
('2025-01-01 09:00:00', '0307777888', '/image/AvartaDefault.jpg', 'ethanwong@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'MARKETING_TEAM', 'ACTIVE', 'EthanWong'),

('2025-01-01 10:20:00', '0318888999', '/image/AvartaDefault.jpg', 'chloetruong@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'MARKETING_TEAM', 'ACTIVE', 'ChloeTruong'),

('2025-01-01 11:45:00', '0939999000', '/image/AvartaDefault.jpg', 'nathanle@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'MARKETING_TEAM', 'BANNED', 'NathanLe'),

('2025-01-01 13:10:00', '09371111222', '/image/AvartaDefault.jpg', 'isabellapham@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'MARKETING_TEAM', 'ACTIVE', 'IsabellaPham'),

('2025-01-01 14:30:00', '0942222333', '/image/AvartaDefault.jpg', 'lucasnguyen@learningZ.edu.com', 
 '$2a$12$z85sSft.4n.ervRVnwEg9OPP8oh0sq/zVsQ1juTfpRG3DopQxPlPi', 'MARKETING_TEAM', 'BANNED', 'LucasNguyen');

--Grades
--- Insert into grade
INSERT [dbo].[grades] ([description], [name]) VALUES (N'For Grade 10 students', N'Grade 10')
GO
INSERT [dbo].[grades] ([description], [name]) VALUES (N'For Grade 11 students', N'Grade 11')
GO
INSERT [dbo].[grades] ([description], [name]) VALUES (N'For Grade 12 students', N'Grade 12')
GO


--subjects
---- insert to subjects
INSERT [dbo].[subjects] ([description], [name]) VALUES (N'The mathematics for student', N'Mathematics')
GO
INSERT [dbo].[subjects] ([description], [name]) VALUES (N'Literature for student', N'Literature')
GO
INSERT [dbo].[subjects] ([description], [name]) VALUES (N' The English for students', N'English')
GO
INSERT [dbo].[subjects] ([description], [name]) VALUES (N'for students', N'Physics')
GO
INSERT [dbo].[subjects] ([description], [name]) VALUES (N'Chemistry for students', N'Chemistry')
GO
INSERT [dbo].[subjects] ([description], [name]) VALUES (N'Biology for students', N'Biology')


--courses
---insert to courses
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (41, 1, 1, N'Math Grade 10', N'1Ytj_hOd_t957hyJ_xKCQ_-RxDIYAN0I1', N'https://lh3.googleusercontent.com/d/140Sl9_j7ttlobjOiEyzLHZArN5Mwa3t8', N'ACTIVE', N'The Grade 10 Mathematics course is designed based on the official textbook, consisting of 9 chapters, 4 practical experience activities, and 8 specialized topics. Its goal is to help students grasp fundamental mathematical concepts, apply their knowledge to real-life situations, and solve practical problems. The course follows a structured and progressive approach, allowing students to easily follow along, study effectively, and gain confidence in their classroom learning. Mathematical concepts are introduced through real-life situations and illustrative examples, combined with the use of technology and visual effects to make learning more engaging and accessible while enhancing students'' understanding of core ideas. The examples and exercises are carefully selected to be representative and progressive, ranging from basic to advanced levels, enabling students to study at a pace that matches their abilities. Additionally, guided note-taking sheets are provided to help students summarize key ideas while minimizing excessive copying, encouraging exploration, inquiry, critical thinking, and deeper reflection on mathematical concepts. The self-practice exercise system reinforces fundamental concepts, while real-world application problems help students connect learned concepts to practical scenarios. Furthermore, the exercises are structured with increasing levels of difficulty, allowing students to choose and practice based on their abilities, learning needs, and personal study conditions.', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (42, 1, 2, N'Literature Grade 10', N'1rkOUe0cDNEIcSubgf-uE4J7jPEeO-b3q', N'https://lh3.googleusercontent.com/d/1Qj6cBkEMTPgJKqxDgUDX4gp5hXERNr9s', N'INACTIVE', N'The Grade 10 Literature will help students develop Vietnamese language skills, cultivate a love for literature, and enhance their comprehension, argumentative, and critical thinking abilities. With engaging and detailed lessons, the course encourages independent study while incorporating practical exercises that connect to real-life contexts, allowing students to assess and reinforce their knowledge effectively.', N'Note 1')
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (43, 1, 3, N'English Grade 10', N'1TtwOEk4SWqJIZk9Pfgi5nzzoZAfNecjw', N'https://lh3.googleusercontent.com/d/1bLC0oLUBNZvgzyqfn8khk0ZkHHj49j_E', N'ACTIVE', N'The course offers a comprehensive exercise system aligned with the textbook while also providing additional exercises following the high school graduation exam format. Students not only review vocabulary and grammar according to the curriculum but also expand their knowledge and familiarize themselves with exam question types. To achieve the best results, learners should complete all lessons, note any questions for discussion, and actively practice exercises to assess their understanding and application of theoretical concepts.', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (44, 1, 4, N'Physics Grade 10', N'1nCjjw0QreLq1FFIWmqY2FJEVRc5QhVO_', N'https://lh3.googleusercontent.com/d/14Y92t2pKDuQtSESPuCCEa4VWRhuVm--2', N'INACTIVE', N'Basic and practical Physics course for Grade 10', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (41, 2, 1, N'Math Grade 11', N'1AVafXpniuRxod5hHU23ouDff7WZ0g3Lj', N'https://lh3.googleusercontent.com/d/1myMaRwL_Gh08OkjL1Q58Ll-ei7v_2P50', N'ACTIVE', N'The Grade 11 Mathematics course, part of the Connecting Knowledge with Life series, is designed to support students following this curriculum. Built closely around the content of the second semester textbook, the course aims to help students grasp fundamental mathematical concepts, apply their knowledge to real-life situations, and solve practical problems. Lessons utilize illustrative examples to clarify key concepts, fostering a deeper understanding and logical thinking. Teachers guide students through problem-solving processes using real-world scenarios and structured exercises that progress from basic to advanced levels.', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (42, 2, 2, N'Literature Grade 11', N'1gyN6hg9TV6aGBBJL2DA64-665RRAvB_Y', N'https://lh3.googleusercontent.com/d/1LsoQt0qTluaF9HwrgI_eVcUqTyQqgZYh', N'REJECTED', N'This course equips students with essential knowledge of literary forms and genres covered in the high school Literature curriculum. It strengthens and refines reading comprehension skills by analyzing texts based on their literary characteristics and structural features. Additionally, it provides a comprehensive system of Vietnamese language knowledge, including vocabulary, grammar, text composition, and rhetorical devices, enabling students to answer questions at recognition and comprehension levels. The course also organizes a list of significant literary works by genre to aid in review preparation. Furthermore, it helps students identify key characteristics of different literary periods, notable authors from various movements, and compile a list of important authors and works that are highly likely to appear in exams.','Not good enough')
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (43, 2, 3, N'English Grade 11', N'1Yqx3GdbM3z_ZlBI07MXilHJqKv206Xy4', N'https://lh3.googleusercontent.com/d/1k_8rygWKpLroD5Hhb3dE6IUWdnQabIrV', N'ACTIVE', N'The knowledge system is structured sequentially according to the units in the textbook, allowing students to quickly and accurately apply learned content to solving exercises. It also supports students in reviewing for semester exams and achieving good scores in class tests.', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (44, 2, 5, N'Chemistry Grade 11', N'1FsfAOxII6G5oRPnXgoDgM0uT4he82yJk', N'https://lh3.googleusercontent.com/d/1F4n8aQiT8veh6dpjPtR0U4TeT67wyN4N', N'INACTIVE', N'Advanced Chemistry course with practical exercises for Grade 11', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (41, 3, 1, N'Math Grade 12', N'1PEr1H9_hIQkAnCuy31rthfYmrOEL_d5E', N'https://lh3.googleusercontent.com/d/1EwLXZvTn3oSKG0k3XU7IfJq59Rqog4ea', N'ACTIVE', N'The Math Grade 12 course equips students with essential and core Grade 12 math knowledge, providing a solid foundation before engaging in intensive exam preparation. Instead of introducing concepts as in textbooks, this course focuses on systematizing key knowledge areas. Each lesson classifies different types of exercises and includes illustrative examples aligned with the high school graduation exam format, with an emphasis on real-life applications. Additionally, students will receive guidance on solving comprehensive problem types by topic, fostering analytical thinking and problem-solving skills effectively.', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (42, 3, 2, N'Literature Grade 12', N'1mitz_f674dFBR7pAJFhg-3xKvDrJSeAN', N'https://lh3.googleusercontent.com/d/1XHzHLlnjrGyW3VqwdEsTK-wHrr5uFPpM', N'PENDING', N'Literature course reviewing key literary works for Grade 12','Add new course named Literature Grade 12')
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (43, 3, 3, N'English Grade 12', N'1H23r0ReEJDHBmfxr2WoRCcvcCeHLyMGx', N'https://lh3.googleusercontent.com/d/1coaUgNQkDyXZRuwos4hP2rJxmGWXXGSo', N'ACTIVE', N'Through interactive online lessons, students will build a strong foundation and gain confidence for the high school graduation exam. The course follows a structured four-step learning path: delivering comprehensive and engaging content to help students deeply understand textbook material, integrating a detailed practice system within each lesson for immediate application, providing interactive support under each lecture for real-time clarification, and conducting unit-based assessments to help students reflect on their progress and adjust their learning approach accordingly.', NULL)
GO
INSERT [dbo].[courses] ([created_by], [grade_id], [subject_id], [title], [course_drive_link], [course_image_url], [course_status], [description], [notes]) VALUES (44, 3, 6, N'Biology Grade 12', N'1eNW0kZv4vpHPLVmW0SdDnflCrVZiIQei', N'https://lh3.googleusercontent.com/d/1jmK1R2MlHWAG8vkss-18-83XUBEmd-39', N'REJECTED', N'University entrance exam Biology course with in-depth exercises','Not good enough')
GO



---- Insert to chapters in Math 10
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 1, N'16E9gtbO7F0RbZkaG49EcpBC3ycO7y3k2', N'Propositions and Sets', N'Students will explore Propositions and Sets, covering topics such as propositions, variable propositions, negations of propositions, conditional propositions, converse propositions, equivalent propositions, set operations, and more.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (2, 1, N'1WcJWRMZ1XHNn5HGeCgAOZvJ-ILhMrnLz', N'First-degree inequalities and systems of inequalities in two variables', N'Students will explore first-degree inequalities in two variables, systems of first-degree inequalities in two variables, and how to represent the solution regions of inequalities and systems of inequalities.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (3, 1, N'1XvakkKAD0f5C-5mIa-dTLxB5tYru1rsL', N'Trigonometric identities in a triangle', N'Introduces key formulas such as the sine theorem, cosine theorem, and relationships between sides, angles, and altitudes in a triangle. Students will learn to apply these identities to solve geometric and real-world problems.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (4, 1, N'17gTDI_hBWmmMLVrYfbm9ZmefQ24p30sw', N'Vectors', N'This chapter introduces vectors, including vector operations, scalar multiplication, and vector representation in coordinate planes.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (5, 1, N'1AxayzE7VD-B1URyRZyIE4sVhfui_yX2u', N'Statistical Measures of Ungrouped Data', N'Students will learn about approximation and errors, central tendency measures, and dispersion measures to analyze statistical data.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (6, 1, N'1AcjHHqJCD7WP8DFq8bmTBYiREO0ANyuq', N' Functions, Graphs, and Applications', N'This chapter covers fundamental function concepts, quadratic functions, sign variations of quadratic expressions, and quadratic equations.')
GO

 ---- Insert chapter to Literature 10
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 2, N'1vcare0mW2v9V2hhNTBVK7jWGBrbuhPeE', N'The Appeal of Storytelling', N'Explore the power of storytelling in reflecting the world, conveying ideas, and preserving cultural values.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (2, 2, N'1c-Eou-Qvi2BioJJW6MFXRBcNjrGf9NRQ', N'The Beauty of Poetry', N'Delve into the art of poetry, exploring its emotional depth, unique expressions, and rhythmic qualities.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (3, 2, N'1KGc-RGfOvcIC8TcvgGdBGStc11-y9Dhp', N'The Art of Persuasion in Argumentative Writing', N'Discover how language and rhetoric shape arguments and persuade audiences.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (4, 2, N'1QcOzP1zK_Fx4T7nWwSiZnz0dp95IZ008', N'The Vitality of Epic Poetry', N'Examine the heroic themes, cultural values, and narrative power of epic poetry.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (5, 2, N'1sFTsjhwt00a5hkjkXYPeybByJoTWRZzZ', N'Folk Theater and Traditional Performance', N'Explore Vietnam’s rich tradition of folk theater, legends, and dramatic storytelling.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (6, 2, N'1AEtHwe2ydeXcsGI76M4kIoJ6vsaow5Bs', N'Nguyễn Trãi – A Lifelong Devotion to the Nation', N'Study the life, thoughts, and literary legacy of the great scholar Nguyễn Trãi.')
GO

---- Insert chapter in Eng10
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 3, N'1O7S6AM-FkKmJnVxnfB5oW3g1yxINE2Oo', N'Introduction', N'In this lesson, students will explore vocabulary related to hobbies and physical appearance, as well as compare the structure of the Present Simple and Present Continuous tenses.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (2, 3, N'122tCPzFECLbImGKEdoyYVmwe2C1rgmJ9', N'Feelings', N'With the Vocabulary, Grammar, Word Skills, and Culture sections, along with the four main skills—Reading, Speaking, Listening, and Writing—students will learn new vocabulary, pronunciation, and grammar points.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (3, 3, N'14HmCRUeJZniz-2d-bW2ZXV6Vc4LppkeD', N'Adventure', N'With the Vocabulary, Grammar, Word Skills, and Culture sections, along with the four main skills—Reading, Speaking, Listening, and Writing—students will learn new vocabulary, pronunciation, and grammar points.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (4, 3, N'18hs_VLOz3Tp1LFXwOpGhUivZGkQgehHb', N'On screen ', N'The lesson provides comprehensive knowledge, closely following the textbook content, helping students learn new vocabulary, pronunciation, and grammar points.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (5, 3, N'1Z2soVBj4no3RG7XDRLYPDncvxE_XmVYI', N'Our planet', N'Our Planet includes summaries of fundamental knowledge, illustrative exercises, and multiple-choice questions to help students review and prepare for lessons while also developing their skills and vocabulary.')
GO

---Insert chapter to another courses 
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 5, N'1wxG0T9vqYq0H1KY3oQn6t6RZ5WhVCGEo', N'Trigonometric Functions and Trigonometric Equations', N'This chapter introduces trigonometric functions, their properties, and graphs, along with methods for solving trigonometric equations.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 9, N'1b9FEDTbMrP2L52K-PdN3kRVZ_l6d4aoV', N'Application of Derivatives in Analyzing and Graphing Functions', N'Explore the increasing and decreasing behavior of functions, their extrema, and practice solving problems related to finding the maximum and minimum values of functions.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 6, N'1D_NTtby38IW66cs5vJdQmo52ItUqY5ZA', N'Week 1 Literature 11', N'This lesson provides an overview of Vietnamese literature from the beginning of the August Revolution in 1945 to the 20th century, highlighting key literary movements and notable works.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 10, N'1Kc72aGyFJiMOT3EJcAlWih37ya77PhgY', N'Week 1 Literature 12', N'Students will learn how to write argumentative essays on philosophical and moral ideas, developing their critical thinking and analytical writing skills.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 7, N'10t_3nnVjF6vyX_DwjsQ7In6bNUgbGHA2', N'A long and healthy life', N'Helps students expand their vocabulary on healthy eating and lifestyle habits, as well as common phrases related to this topic.')
GO
INSERT [dbo].[chapters] ([chapter_order], [course_id], [chapter_drive_link], [chapter_title], [description]) VALUES (1, 11, N'1WEr2dw9jRJDw3nvgIoH1nnMBpRZ-Z7po', N'Home life', N'Summarizing the lecture, reviewing textbook exercises, and providing multiple-choice questions to help 12th-grade students review and prepare for Unit 1: Home Life in English 12, covering Reading, Speaking, Listening, Language Focus, and Vocabulary.')
GO


---- Insert to lesson in Math 10
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (1, N'Introduction to propositions, including definitions and examples.', N'1rwqE09ZKi1lyx_QORB4bVVrF-oogLG8E', N'1zJ_ze-ho8Rp1_bhABcIJUC5bBGjGA8GX', N'1w0hvmmWDuhXKmDjEBEY3REgI1Ok4LQU2', N'PRACTICE', N'Propositions', N'1YJbFoMgSa4XxqxxIedXz_BPwoHWmQkCT')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (1, N'Covers fundamental set concepts and operations such as union, intersection, and complement.', N'1-yBOFW4sjpBWp_4mikWeERPsvNAkUAZr', N'1iphYf5EVzH1lm3o5_phDoHu0B6vIc1_7', N'1yoQAjEHqhboEEHwqeL1ZIDA_-OcrhP-o', N'PRACTICE', N'Sets and Set Operations', N'1Pyb3zLKOLlMFq9HmD-Sk77z75LhFdF1b')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (1, N'Final Exercises of Chapter 1', N'13MBGIUYa-4FYeCPLcr4YtsCFUi00VJym', N'1Z_clOg1cPfHpTn5sT-tyARBv_eJz58L6', N'1Fo5kYsOl82jWZ5IJ-oWaSxIxMe8QYSOy', N'EXAM', N'Final Exercises of Chapter 1', N'1PbJY12E_JVp_8PwroQ9uKyywxcCK7JY0')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (2, N'Introduction to first-degree inequalities in two variables and their graphical representations.', N'1C8B0onh8WCHNhjK7vM6ZGu0qZjjUDujk', N'1AAjx-3IDuXHXbUlwqZs7s40i7k3sdc9p', N'1e0CbN58wJi0I0VNH5Hvzo8NwO8CgyNDs', N'PRACTICE', N'First-Degree Inequalities with Two Variables', N'1aS_CECZtJmvI2SGUPVkEs7ozXCT_jyiL')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (2, N'Explains how to solve and represent solutions for systems of first-degree inequalities in two variables.', N'1yrU4vpq0It-7ktLiMpDCIjO8fw41nyHb', N'1n_ZAuK2dzox1uwJP5J3MemKMP7KvWz_z', N'1ENzwMB-eUeeUYehfFGH_YqSpAzQeY_IS', N'PRACTICE', N'Systems of First-Degree Inequalities with Two Variables', N'1bIMNDD04qLBCIhZbg03nYOUj38pK2AVy')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (2, N'Final Exercises of Chapter 2', N'1eAcdpEQTSHXvFKzkDyiyHmD5v1_l9b6t', N'1b-zrqjxBwxuyNKmAIKUqzdX9boYXQMOC', N'1ng00di1CXS1MpqZv9eHx0-GTTphKIWeH', N'EXAM', N'Final Exercises of Chapter 2', N'1ZNgyG5F-TqOUErlugb9Bi7I3MQhxog7k')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (3, N'Explores sine, cosine, and tangent functions and their values in the given range.', N'1pSktYqmD1b4_kNbpcYOQUWcLzEp-5j1I', N'1q8EUrWvhnWwRytOUKMqUxhPQaojjrK0X', N'1bY_2IpYJLV6KHLLfChxKcNv0EmxXXORJ', N'PRACTICE', N'Trigonometric Values of an Angle from 0° to 180°', N'1BUSzHE6FoHfd6SyuFtR2X4J_-6Gtq1uZ')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (3, N'Introduces important trigonometric identities and their applications in triangles.', N'1QGymNbXitHoz653kZZXd2ZRxAylrjeUD', N'1KVLASsGTcS0pwu1ZBMzMbXjvJnFqAoxK', N'10BpCV4FR2UvWr-6XgJ3PHxHDmyo7FGkM', N'PRACTICE', N'Trigonometric Identities in Triangles', N'1kT6KRu_Qq49jw5T7Wxm3lPzJ9TNopyNp')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (3, N'Final Exercises of Chapter 3', N'1w7YyBZ3Ll2z4YfOxUxUfw58WO-qjIw5a', N'1CCqEFJBCoiiAoKOQDWaZ_X-G6cGIblY2', N'1bPw-XBRdc6FOSRPXYJspknZ5UYpRwY84', N'EXAM', N'Final Exercises of Chapter 3', N'1HJ8zmk5vsXEO2TcDXqSgCQsWL6cT-6_C')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (4, N'Basic definitions and properties of vectors in mathematics.', N'1g8H9Ncl0QU0I1V02Sgs7Nj9YY6SKoaLH', N'1WfX1iqaQIuDa0FN5cAc-fOTqG4gHuhDo', N'1Q8LJzjkK2qUQR8Kq2ZGI5Z5C4TM251Ws', N'PRACTICE', N' Introduction to Vectors', N'1C_6YaMT1uN8h1Z8UvPo_gya7WTqPBLPz')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (4, N'Explains vector addition and subtraction, including graphical methods.', N'1XztrLQ3oEG8bgxcjEjKMXvlAxJVKeFXq', N'1482YpePCE0_VndLFS92qweQfzLezL4ZQ', N'1EaAZq7ARLLX_FqHuzatjicRJs4Mu9O6t', N'PRACTICE', N'Sum and Difference of Two Vectors', N'1DIOL6WaYnp2PH4VO4U0t_NL94-cTSx0G')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (4, N'Defines scalar multiplication and its geometric interpretation.', N'1AvXhaCTfPCNU9OVcW9tYFZ_uk10jnu-3', N'1i7a9p9coDNorhfwH-Rc7fsnOHZuh80Ir', N'1hCXSAFmtDZxhwDAYncDhFXdgq4veYjMH', N'PRACTICE', N'Scalar Multiplication of a Vector', N'1HqX921XBbJrvodaEYZqLHekQxkpR8RVV')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (4, N'Discusses how vectors are represented in a coordinate system.', N'1nMzb58n85kmpdbmoh4BwDmxRMAdFsOgh', N'184Ls-pG4pSdCWfin3x1wwt1vbmDTSQHI', N'1B08_FhbuT__hKT5T1y2RjNolb8BrIlKo', N'PRACTICE', N'Vectors in Coordinate Planes', N'11kGXNFqQZvy6rzk3czzxFD54a-X3XgFD')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (4, N'Explains the concept of the dot product and its applications.', N'1u8mjDuSZFwylqbPyTDKzo8_DT9xWR7Nr', N'1VjUWQCAvxXBMLzCrXeLz-9BX5OU8Cb6d', N'1VPdalMAqVZ88Q_dvt1UiZTLilsFKSkzi', N'PRACTICE', N'Dot Product of Two Vectors', N'1G5Om0jpQIEYB3ycMvdd-yIPXBosa9mVq')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (4, N'Final Exercises of Chapter 4', N'10_wejK1k1yK0pnw_B1sQ8XLhXMRcDSZh', N'1rpIkBRseqSCBqfcjsrGbmURAHlUWUU-J', N'1Cbi3Bc5BDXJb5IkHyOPd8IMC8_1B5UHm', N'EXAM', N'Final Exercises of Chapter 4', N'1sQv2PDT5GCwyZ3BG3L_fJPPdsw7vmSrl')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (5, N'Introduces concepts of rounding and measurement errors.', N'19NBArdPGC6EMYPoa-A5NZoyNG4hXDYAc', N'1VLsxY57niFiKPzCt2DudaG19P6sgLhka', N'1-fyesH-qgYKvN6lzdep3Xw-9tEFf1Fhb', N'PRACTICE', N'Approximate Numbers and Errors', N'1qywhFv8mbBciaWgJXb96nnl47C7k3icM')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (5, N'Covers mean, median, and mode as key statistical measures.', N'17MKYdyZe4bovIx65PIVLePucIC9zhqoQ', N'19nSwLEkuaCH5-fTknvY1xnMrHhs1CEJC', N'1uPCrJutNvNNutSHhwZeJQZT7lSBLF1dy', N'PRACTICE', N'Central Tendency Measures', N'1cCZf6ahog5iEykiBUY1YwXvGmk0F3FRn')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (5, N'Explains range, variance, and standard deviation in statistics.', N'1k12gaL_n_o02JOf7JC9CcQhAbuMna1AQ', N'1AurkVS9WrkLQmNSVFjrvyHfGX-SKyEED', N'1AFstruyI-S7tlqhlnB_HIb1gfmSht7gh', N'PRACTICE', N'Dispersion Measures', N'1mGnfy7MINRNxOR0DiCSjcpgUi_Xk2uQq')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (5, N'Final Exercises of Chapter 5', N'1nIjFuNrtrdU1260bDD_1lA71scwscOeo', N'1-lHSuqnUi_wV63Mhw6W6r32YfxWHt6bJ', N'1QWvj5O72bA8zIw-K6BlQPbSErmxUhr_0', N'EXAM', N'Final Exercises of Chapter 5', N'1OrZAV9iuK4I600PcHrXuXupDRolQaGKr')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (6, N'Introduces the concept of functions and their properties.', N'1_E0LQneXLZHzBc4mAz0JEmAlYt6f1ql6', N'1KtzFmfa_wpdiPwpKzOZId1CuhXf33ADk', N'1EG3aVD3A-NCjW4ZphNJwdj_07jRHADtK', N'PRACTICE', N'Functions', N'1vKLbdRPTFl1V4nzNXXjPeND7wOjKYdkw')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (6, N'Discusses the characteristics and graphs of quadratic functions.', N'1Q4W9tttTx6uzBhtLJhjayZfOOXjSgAGt', N'1_xdvRAAKYAi8aZNPMXtW7NrfdhPrO1wk', N'1AN272mv7XMtXIvnTSOQIEdUfjMSitHJ-', N'PRACTICE', N'Quadratic Functions', N'1wf03yA-UhA3oVqF7BxE3aq-jjUBbErdM')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (6, N'Explains how to determine the signs of quadratic expressions and their inequalities.', N'1VTwQUku45YIEjrIz4b-pzwHqc1hGcaga', N'1-l98zbPoYi4xV2fvtb4jvTEiXF2gav0Z', N'1nXbD3zvJ2i-FBsAnKVeHFEuuZ8ezpng8', N'PRACTICE', N'Quadratic Inequality Signs', N'1ZpTSKvI7QmasKzcEuHy56gfV0uy06XxH')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (6, N'Covers solving quadratic equations and their real-world applications.', N'19Q410JJOi5l0dXA433Vo3n4EdTWx2AZB', N'1jf5i_2aCcDr4PCAVWLAJPam-AME0cPQF', N'1uiUGVtENg_kxRiZEM7HPxhL5pUK-Th_u', N'PRACTICE', N'Quadratic and Rational Equations', N'1m4thbLTvbYDs2B69iT7bdvrct5gPaR3n')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (6, N'Final Exercises of Chapter 6', N'1jT382Ka7hblz2LE6mhdzxNTPX62vMTgX', N'1MF4AGmTnJbb5KQWdDpCULhh-BWvrCMO_', N'1QpBVHKj9PxJ_hVxqVsLcGmMrDlkP5Exg', N'EXAM', N'Final Exercises of Chapter 6', N'1V1oTnTQwURPk89mPXXqPid4raPpGa76R')
GO

---- Insert lesson to chapters in Literature 10
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (7, N'Introduces myths about the formation of the world, helping students understand how people interpret the origins of the universe through storytelling.', N'1bbK4PRL1etJVv8mtuaomVJ-BpH5H291L', N'1gWzMkmkzThw-oWBkQ_tY5F1_Y7zacyt8', N'1PEvW3wlb1Yt6Ef6Ux_2doZXSvBAP0pGP', N'PRACTICE', N' Myths of the Creation of the World', N'1gp-LcMV7Nslui5lIkm-VKfjHk4YPISZE')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (7, N'Discover the legend of Tản Viên, highlighting the pride in heroic figures within Vietnamese folklore.', N'1MtdQZFmt4SH4jBXHCaOCMjH7rN1zsbyg', N'1LIFZMqZLrNSPwE0180Vd3JZYC4_F-iXP', N'1Abpis_hpX3jQcwrUOCT4yWsXU3Mb5X4-', N'PRACTICE', N'Tản Viên from "Phấn sự lục" – Nguyễn Dữ', N'1hIAEk-xm6sQ4OQco03UWLy3OI8G7hnNU')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (7, N'Explore the beauty of talent and dignity in the character Huấn Cao while appreciating Nguyễn Tuân''s distinctive literary style.', N'1TnytMVxUUz16fAB6tGHcsdbcYzmfcUM9', N'1MNbBBamLEsCgSic12Q9uEPb56Yqm-Cyf', N'1jmsVaFtcqcAXBCloEo_9f6ceb2Y1lms5', N'PRACTICE', N'Chữ người tử tù (The Word of a Death Row Inmate) – Nguyễn Tuân', N'1DOEExC_f4ZDXUpa4iNfBPW6APbyFHGdx')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (7, N'Review and practice Vietnamese language skills related to the lesson content.', N'1xPV4r2BJ25P-xdZFFw6buDdEUCFY_G-8', N'1eiwJ2M9nb6371HQJ3ZBi3rw6QOFpUtXU', N'1_oMZKfI8cEuLuo7tWXL-M1c_0VDTL8Qe', N'PRACTICE', N'Vietnamese Language Practice', N'1zbx-3NgJ5BegD0YpwxPDD8sgEZ9cKxrg')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (7, N'Guides students in writing analytical and evaluative essays on literary works, developing critical thinking and reasoning skills.', N'1kc-N1Nf-5Ah8Yv734S7a33x0dDbvQKEB', N'1rgz4ImApg8JPrzt6DaPIiPnOGYbPcjfi', N'1wNEqnRcsqgTfeEl5TFsKrz7zsF-8LoNH', N'PRACTICE', N'Writing an Analytical and Evaluative Essay on a Literary Work', N'1WFlartQ1JcMGgZPmD8IsvaSe_KlIBdku')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (7, N'Review and expand knowledge of the stories learned, helping students gain a deeper understanding of their content and meaning.', N'1HVzN_0T68RduH9pVQabcyMvrwuTFKx7u', N'1q6L-te-bS1ZMKP9rHz0LLT8quRobXT-6', N'1jrHQmBYaYfrgxLosDnmSAn2nQivawGw9', N'EXAM', N'Reinforcement and Expansion of Chapter 1', N'1DiLXy8nO8laZTcE4vD8-glRwZyxa3YT3')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (8, N'Study the concise yet profound beauty of Haiku poetry, focusing on nature and fleeting moments.', N'109D_e2p9_Dflz11gRyc7Q1MIybLP9E5A', N'1SCAt4dYg4go2-fu8L8uIXqxCLhVB7L-T', N'1ZOCc06RqBlXRql-iuqVpUaLNlPmmPhYK', N'PRACTICE', N'Collection of Japanese Haiku Poetry', N'1-JxxgBcniFTdxc4TB-GNAhrEnswwBaXg')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (8, N'Analyze the melancholy and deep emotions in Du Fu''s famous poem about autumn.', N'1JIfVukcW8_vHnHv9bRuch3Iv_dmuwTz_', N'1Jj3fdgUm33BuHkOmS_RDQdgvclfTk8o-', N'1RqN7s-bSGXLRYOt0lKmeK4qwvhwyd57Y', N'PRACTICE', N'"Autumn Sentiment" - Du Fu', N'1YIo7OOyIEq7mvpMu2BlnEecFvt6QQi8p')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (8, N'Appreciate the vivid imagery and lyrical beauty of spring in this celebrated Vietnamese poem.', N'1GxsshYlvQEMYkyD2Ovt3GeYz_Ir_WFbs', N'17zFDnWVd4_bQm5x6u9NOWxr01kHGjd57', N'1QmaM9lavm0gN3alvcwdsLAhEK2rv0uqC', N'PRACTICE', N'"Spring Morning" - Hàn Mặc Tử', N'1ln1x3uogMIh7z70-w3iQxsw6jCrq5P5v')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (8, N'Examine how words and rhythms create a harmonious musical quality in poetry.', N'1dFEug_YfJO8an2ECZqrGrd8Dc80qTRjJ', N'14utBdt4evvGlwSTIfa-jbBa7XL-6x4_1', N'1WNJc99bmiiI89yPeCdOyBchg0f0uxl9l', N'PRACTICE', N'The Phonetic and Rhythmic Harmony in "Lưu Trọng Lư’s Poetry" - Chu Văn Sơn', N'1xlNLQ4btFgnXHvj-Ef55KSCcbFaha9nw')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (8, N'Strengthen language skills through exercises related to poetic techniques and expressions.', N'1TDiZ7C_qfKW7jgC6WbGnN8fuk0OPgUHh', N'1j7OnVr94Nmq3Z5kKZ1lxsuMapkWUDwPJ', N'1O19SeKJoFr2TYvrBiTcCMLL5nKwIHkFZ', N'PRACTICE', N'Vietnamese Language Practice', N'1cBDe5XqiarH3OH7ZMyFJvjo1jtTqtylU')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (8, N'Develop critical writing skills by analyzing and interpreting a poem’s themes and techniques.', N'1gI25CGg0ESPke-wCSyjnb_Q6z3TxQGL8', N'1Cdz6ZFAPx5t_-Id6xCbxmTulniQQlFsa', N'1pODBMyP5o7MaoAD4no_brb55NL08guko', N'PRACTICE', N'Writing an Analytical Essay Evaluating a Poem', N'1wPlsCosvNqwdli00wJhuVxo7WbhZv8KB')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (8, N'Reinforce knowledge and explore deeper insights into poetic styles and meanings.', N'1p5v5Xa7Qc1A_-2V1SUxvMP7WyoIVQNjc', N'13n3uqVNPHjQ58Z2J_MIk6dyjPRin2ouv', N'1F1QL4iRNMJIb384rjOBVbR9eUNJIdDBZ', N'EXAM', N'Review and Expand on Chapter 2', N'1KTtCpJw4aUp9972skl7idlUsZD7Lp7fg')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (9, N'Explore the role of intellectuals and talent in the prosperity of a nation.', N'1DxUGpbnWxTQgHp5jGembeTXoFJTLL9yC', N'1LxsA6UBQzuMlcZtU_BLvwVFhSOl6uj72', N'1Jo2s_7ozXCfEy-qgnxDYEzyoTe4-8NCN', N'PRACTICE', N'"Talent is National Strength" - Thân Nhân Trung', N'12ixlN6r_6E7QsnEK9qNM6ffT1GypmsWY')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (9, N'Discuss the importance of empathy and human connection in life and literature.', N'1i384hJP17_Kwg2BS2XMmQzyJaSathEuA', N'1gsQ6FvLJpLTldEtyAmcXXqALGwUUQkxs', N'1d2Jyget2Q6HfamKO8fPlLjVcupyGpmWO', N'PRACTICE', N'"Love and Compassion" - Phong Tử Khải', N'1733Ne6GiF2h6O5Y0K__vYt7MV0ZCvYhm')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (9, N'Investigate the creative power of language and how words influence poetry and thought.', N'1CJ_ZSNLnTxZM1n9rhVzo4PoEe3PpN1Ue', N'1houktBebxxE8bEuohRQ5kcLM0CCKpLhg', N'1LALtRnatGrV6j5EwHfc25Vb86rHrByZ5', N'PRACTICE', N'"Words Sprout in Poetry" - Lê Đạt', N'1TQgiTwc8g4uvUewDhpj9cpP8pSOfF4um')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (9, N'Practice language skills related to argumentation and persuasive techniques.', N'1BoaKHbUgKsCmrLL1EsGxbIdRfembTIY6', N'19WyXoEJR2eD7ruK69oPCMDaSd-QJ1gJA', N'1yQXL0O0sb9MDq7x7gZc8iNv97z91Cfez', N'PRACTICE', N'Vietnamese Language Practice', N'1sDX2px3MCAYHurzt9ZXGWmpN7bFsd_Nd')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (9, N'Develop persuasive writing skills by constructing well-reasoned arguments.', N'1IbF0zY50WXmXIiCJSpJMZQaRxe5j0i4T', N'1hL6WbRA-yymV36imQOwIVL3w2g77GrAe', N'1QTLxESY1lFQvCOfv-NFvX7y-_XTPBDeN', N'PRACTICE', N'Writing an Argumentative Essay to Persuade Someone to Change a Habit or Belief', N'1fzxGlVqzzhvtVBrkWelA5I-TqY9yr2Vb')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (9, N'Reflect on the key rhetorical strategies used in argumentative texts.', N'1ge91djMPB5D7I6QfPpazAqzOM4ZriTqo', N'1GDbd7UimlQSSIncpV01lZv_opR3hhiYb', N'17_mnTHbZLZzKP1zkP2bFFQAAG5yW_hS_', N'EXAM', N'Review and Expand on Chapter 3', N'1HAXoD1BJuNO4LP20cBCmtedOC6QMVl6j')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (10, N'Analyze the tragic yet noble departure of Hector in The Iliad.', N'120GETfdqTGbHA4MvS5-GPxPDbgPB59zN', N'1U4KniVkxvWA3DNYMIQTUWQNmsT1LlpLE', N'1xvUTM9GzcR4LY7fY2aKeRO2r0T9FDAlO', N'PRACTICE', N'"Hector’s Farewell" - Homer', N'1tb58dnIM1jnWGMvLOKIPF3-r5vP6W5Aw')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (10, N'Discover the heroic journey and mythical elements in this Vietnamese epic.', N'1j_M4Z7mo2n-vy2u1_EmH6DaPkHLEXplh', N'1XGp4DPgBDSQ90I5XdXo_oXltRfYsfU_h', N'1aZJuvKGMc-bahZKPUrTUC6kif-SPw04R', N'PRACTICE', N'"Damn San and the Goddess of the Sun"', N'16IMfIcDcRy_S6tRP7ehbawRHmMraxDGx')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (10, N'Improve comprehension and vocabulary through exercises based on epic poetry.', N'1DAQVyYR-fqCWXOe3ab7xnJ3mLTQnEHr0', N'1zcL24hkjtKqIVbvS5wCk1GmRpMPyZyys', N'1g1vnrmKAt8JCQwohVp42KdSxEwSKzVe6', N'PRACTICE', N'Vietnamese Language Practice', N'1q86jU0DSV0-VIqbYItbvN6NcoYLRE8B0')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (10, N'Learn how to conduct literary analysis and present research findings effectively.', N'1JFFrkCcKCLZJWB2lXxPcbDbLPIpOVEf1', N'1yMvkzbEfiOInkj_9Z3PbQF_eqb7PlD1F', N'1UyipEpDItwl893JWcGToa0LBbk484WYD', N'PRACTICE', N'Writing a Research Report on a Literary Work', N'1t56BiQZ2WCLGIl9r6OWzCP6qxh2yGJfK')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (10, N'Reinforce understanding of epic narratives and their enduring impact.', N'1dTCHzedmftO-TUEWSYiv-kLWeza6Kdvg', N'1RcTrYtyegDvZbQ8XDT5Kq1TEuCFZx71l', N'185eZzmJ9qHdWDJ3NQdnqD8y3kH47f-Pm', N'EXAM', N'Review and Expand on Chapter 4', N'1uLNgm-oqrsI0Dyp5xpn075QUZo7v9BVU')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (11, N'Analyze a famous chèo (Vietnamese traditional opera) performance about love and fate.', N'1FpujnF5HkmUg_f-4SdawPOTMeMx19f6G', N'1EnPkbCuFYFQbddbrYupSEPDDdKZ8K5Ld', N'1ca1f1KOBS4I9qNEsNCO1IUJNzrb5WpjO', N'PRACTICE', N'"Xúy Vân Goes Mad"', N'1ituK-5heeov4C6IIxpLshop4uYOnPO5X')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (11, N'Discover a folk tale that reflects moral lessons and cultural values.', N'1NrzUNKVlXHj0jhZix9s4n920lE4CpH5f', N'1AjGVMrZCtuGtKgGD6emrrQ9OEhUowpzX', N'14bZFaXBpPHi0qw7zAbDI2Ex5r2ArBh5R', N'PRACTICE', N'"Huyền Đường"', N'1ym9QcECkNw36aMueHgcd32Ce56FmOSRi')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (11, N'Learn about Vietnam’s unique art forms and their historical significance.', N'1mkEwxrhEnYqje3A34mf-6p0WJM06j3PW', N'10I1_vSzYjrBc6mpAlXQayUyPmn0p02tK', N'1EsZ34zajLtpXLhLjQoJrXCGA72iUe3VV', N'PRACTICE', N'"Water Puppetry and Shadow Theater"', N'1ZoChPGL3P2sWrjCfOomVaDAH562QUd6T')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (11, N'Develop research and analytical skills by studying traditional Vietnamese performance arts.', N'1b-IF90e_FfO4ZhR4h4PbuEwEWM92LfUq', N'1EMp8KdKQPoSipGK8Mr_vWTC_RxQEYI8C', N'1qEaKqrlGePm9Hfa2CY3j-i3WRMR-ApiX', N'PRACTICE', N'Writing a Research Report on Vietnamese Folk Arts', N'1CY2XxcaWPPgFSxkZziOOnaGk2jC-1caD')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (11, N'Summarize key takeaways and deepen understanding of folk performances.', N'1Iehas_BW2Xk-oLOJ146i6IP0R1tQvOMR', N'1LMJ9E8FBpHCGWFr3ki_-2KGJh6Rlio6n', N'1Gd8IRFHwh0xZjs9eJoXgV6akjK-3Alst', N'EXAM', N'Review and Expand on Chapter 5', N'1WB1CKSadbqi0opUKsMHdcpsH_VndVGHV')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (12, N'Explore the historical contributions and literary works of Nguyễn Trãi.', N'18N8qb1o9Sm7Vig9N5xC9jcIUbKdT4tel', N'1KvDVLxX4059rYjhfNVl65zwXdt9eY3qz', N'1SyL4Z3uJG0aXLOzc12y2IFzxP5RSU0Z8', N'PRACTICE', N'About Nguyễn Trãi', N'1EsV0P1dFtLoC_puYZb7AY4AZ6qgYXUPc')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (12, N'Analyze this famous declaration of independence and its powerful rhetoric.', N'1SDADyuZWoQ7swxtiDr_Wycaq66uv0KPv', N'1NPblaffOAmjy9E9uMLWge5Nx6zJTkmhb', N'1fELpm6qP3BCQtYtvn8ynCZ_1rS7-Mto6', N'PRACTICE', N'"Bình Ngô Đại Cáo"', N'1i3F8iLtF1pd4Z23G57SaSovRl7zLIeYg')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (12, N'Understand Nguyễn Trãi’s philosophical reflections on morality and governance.', N'1QcVznn64QT-t8Whx7HADFUnUYEVrpRoM', N'1w-MLgweeDAVwNJCtrgFmiq7NhltpwbjU', N'1wtIcJm0LkWmQ4VjfYO2hjY_D7rsCpnqI', N'PRACTICE', N'"Báo Kính Cảnh Giới"', N'12_YxTBDgi5my3jScJpejVf43NfpiOTUL')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (12, N'Appreciate the poetic elegance in Nguyễn Trãi’s depiction of nature.', N'1wt0-GMb4OQbzI24SlsF3a80XMlhoPEcb', N'1vmeJb-bTAScswwC97wI0yDo3IRntjjSL', N'1NurMxJxKVo2zyZxwqjV5rM8dRQFqI-xN', N'PRACTICE', N'"Dục Thúy Sơn"', N'1z2Gdw3f458QjdJJYhkbMtliPZdtmvc2x')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (12, N'Enhance linguistic skills through exercises related to Nguyễn Trãi’s works.', N'1pOFAN965_8YlqHe__Zspo1rh0c7D4ZaT', N'1hg8MDSP4FC4amBhdrLB5L_NedIRwuxVh', N'1HFPVDCmfw6zAjFSbQ0T5lOnuLYCreW_V', N'PRACTICE', N'Vietnamese Language Practice ', N'1li-sg6Qc0Egk-JUtAzxEulz7eDzz4mby')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (12, N'Develop skills in constructing well-reasoned arguments based on real-world topics.', N'1xNdO1gLwBK_pJCboqartiMSmm7HqD0gy', N'1Cmm5fECp5bN7-W5eIJtRm0XqlSVcmGA1', N'1AHcJ3B0kTVbY8Dqd7v6ImsGxyuDUOk3_', N'PRACTICE', N'Writing an Argumentative Essay on a Social Issue', N'1CcS4BSwFD-mCWiWtZ0yJW2KNIJkFxzAq')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (12, N'Reflect on Nguyễn Trãi’s contributions and their modern-day relevance.', N'1hFSlWF4OJ360MLKMiraCmE-nkUkkcS6z', N'1peCLWwn4QKYfSCARDaplJIazVRGhP3QD', N'1hd8LLKXI0yUiVHuj18YERkX4VGk1YDAq', N'EXAM', N'Review and Expand on Chapter 6', N'1y7OELT0BfKUMDGSja9SkFUuXT5byfIEf')
GO


--- Insert lesson to chapters in Eng10
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (13, N'Introduction - Vocabulary 1', N'1QT1GQAekUjcEAIQzIAszDvCFkDzOOA3X', N'1Y5ndD_kWf3WDXBf6IfJjYia08jCKogib', N'1fxwY1nUpFtIW8-svOIIeXakq6zeaZE-R', N'PRACTICE', N'Introduction - Vocabulary 1', N'1EZvJXWB9Opv10w6_mKftsrvN2q0L2Tev')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (13, N'Introduction - Grammar 1', N'1CtCbmEuFc4Y_GGWKExMMcgoJqdWkW4Ic', N'1jmjgPP4-tDGqWLX30wVshUGO6kbyG3sm', N'1gRHlOx3rxWhZwmVLAY_2XpdBOl3K18cL', N'PRACTICE', N'Introduction - Grammar 1', N'1SpfMmhVPpk4OUh5IFkcFmrzIaQ8I-cYG')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (13, N'Introduction - Vocabulary 2', N'1KJb9WuMM3EKNk_rdIi3gVGuCF0Wu7cK_', N'1K3qkmnJ-ga2EBRlKu0HiiIikfPBnRvqz', N'1Ub4v54BJ7nGrvO1zNI3SFWGVy6MXkWwA', N'PRACTICE', N'Introduction - Vocabulary 2', N'1q-cvMuBwkutsSUQOmUyc_f2A1H-RKfB4')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (13, N'Introduction - Grammar 2', N'19ko4NoxBobq_XElaB28BlyjID8UkjoNE', N'13-IfwJ1QwD3IyYKRu-a2gvtPLzklyNTl', N'1rLeS0qgTxLDYIY8f-MrKUPUFqb_x-9rs', N'PRACTICE', N'Introduction - Grammar 2', N'1H068ftUNCQBMkniobIBT7EEbK_mxCTYk')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (13, N'Review Vocabulary and Grammar', N'1oIEsGBDwiQAg_LnxTnsyNIIeLCMhDESb', N'1Ftyoe6126Dg1jfARjQXMtsk0ZXihqWXX', N'1zsgUlyEosO0rszVz-olSy7fCuOGxoRbB', N'EXAM', N'Review Vocabulary and Grammar', N'1wWi59m3dmXXA1a184-qjSrdBHY9xU3UB')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Vocabulary', N'1vSFjAfAHk8ReJQoJwQwsmPqcZnabhyxF', N'1AyY-pOe_JLc54C4MYepwjIz0ST-mb3GJ', N'1D72JzHXc_gRvXgh6h3uCLxsWDK-WeyqN', N'PRACTICE', N'Vocabulary', N'1Md1tPV2riugiL-ADLlSWlkRU2-FUDeAT')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Grammar 1', N'1w3teuvgD7pMAb1ajp-j3SwZZTX2a4oY3', N'16pl3RK2bT8quLxIVVe-Fi2uZbNeXSrnm', N'108oMqXIYnbUem4rkQZtpxLhFV8EFDeqy', N'PRACTICE', N'Grammar 1', N'1P_0lQ4S-Y7W_Vf2WUBzC1vlz3uljPq6N')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Listening', N'1byZmNqV4YFC0VH9sOmEwcdRk9RM7FeOf', N'1FZ0Iwd1FT9J9pnwnWn8SwOZ3LCraOP6I', N'10UR53w-DL1HjgNcKOzB5V9Sv9leR4krO', N'PRACTICE', N'Listening', N'1-K5jvWEIzT64EzV1XNmif9VxPdM5bA5f')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Grammar 2', N'1oIGvALdPItDfBks6Ww99HHj0sebdoBuH', N'1KKRQ6FY89UWQk7JYMFvfG_NKGIyAD-IM', N'1GsI-pDYa38znTJ_qCNCVx3ctEaHOnO0D', N'PRACTICE', N'Grammar 2', N'1zp1VfxS_Z-ip8c2P4PsYRNvzVi-3L3FM')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Word Skills', N'1yJAmLYgm6y5bzFSHxeDgrJ5GNo4Jb8s1', N'1we1qk9QvSRu5jkmFWs12Tpb7V-g1ip22', N'1lGc-TL0SGYSHtvP6z5nauu1n5GFsXgX5', N'PRACTICE', N'Word Skills', N'1thg6EDPLy2HiWezdACxgYtb4opx_toCU')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Reading', N'1-jg-8IOiIELPJbcm7kSuxGdxW1K-m9wJ', N'1iH_g_tGddxMeHE98qXiNwQhb6cHtfdtv', N'1hODLZEzvXiV-MyBY4MfXud-bLgTkgowL', N'PRACTICE', N'Reading', N'1m8l3H8b9XKV8jUDGG4E8qg87DmsXAtUK')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Speaking', N'1KGLNTJKvQFcThLaIQpwe7ElU9VC880LM', N'1J2sx7GDyYyr3EbjkRbJVWv8NXr8-dFAN', N'10Gw-Efnqg2LPVlPPCWs_rjgnmym0uc76', N'PRACTICE', N'Speaking', N'1D4acWxBhAAXlLSmXzDBHWrv1K0ZoQkj8')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Writing', N'1es7c0Sh4piuqqRnfdu0fsWSBl08iIWmB', N'196NjKvUBmWAAoHTiG99-QZAQ7whccEGK', N'1iiVunJHzVB1GMVT-iAcBGzTW8q04b4Fj', N'PRACTICE', N'Writing', N'1SRDotSYD7XOM1UMb8pqpzRn1ggRfEYrH')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Culture', N'1FPE9gYKUh832u4t_JV7Yvj-Zi1PwGvHu', N'1BX_47YdV2KJB2T5oF0upGaQ1cgKcBg4P', N'1lcFrRUZg7Nbe2qIu-Wsr0biLDa5jAwNE', N'PRACTICE', N'Culture', N'1EC2y7nm7spoOGSQy8IXpxREbyOQ1dzAI')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (14, N'Review Chapter 1', N'17zbw00TaAcWEb0w2zNxMp6nejdnMWCtr', N'1meeYBWWTm6u7PuaJYAdM9JCAWI39sWq3', N'1jGPh2_PY4zvmSVlZRfDezq2cMTTFQ0oc', N'EXAM', N'Review Chapter 1', N'1zTmY3-Ejfb3ejnqagwBIPWjR9kSQdVhc')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Vocabulary', N'1shsKSI2nzxBaclBXiKz8MJUFf3L8ULIO', N'10U7rru5n8KEs8S84RJ4vOzRGam0LDE06', N'1y3KRYFgu1cYL5g1TLZLnRVLjytJdJtQ6', N'PRACTICE', N'Vocabulary', N'1nrrGSQzOmfrLEyWDHOhuqj871d3zhyV3')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Grammar 1', N'1haheRI6KpwzMS-6N4kmbOwIdpcAm-s3p', N'1a14kIZwB_6jEJtqReU8YZCPJ41MuLy8u', N'1969G-b-fnWPXwA4Wnr3q8NdGVSZ12C5S', N'PRACTICE', N'Grammar 1', N'1iY1sWMR1ZMBK1qwgD_EWyqdAs2IhUfYh')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Listening', N'1qhZXeSeyZMGgZyYE9pih75YfGqnjQ-ED', N'1mKVZrE4ko9CzuQ5wUS9Cmn26y6ktnmK9', N'1h7tE11cMH7UiUJMnHVy0c3TdTZMkeTNE', N'PRACTICE', N'Listening', N'1yIU0k8O02lv3o6CLhyUxcLn5fPrDFbfk')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Grammar 2', N'14pUNPYUxWUpIi1s-w4DJWOzeZPsL1iqs', N'1qWnlVerDjPOzG9HTqkP3g6FFRhV3kh1r', N'19ACeSt2PFZAO4SR8aAVOocXslr4ShIUA', N'PRACTICE', N'Grammar 2', N'1zuZJqNdbQl4QaEY4Rfi5NyzNbvHhr3JK')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Word Skills', N'1iGkm2xjvl_hAfoeKU1pisTCW8Id9y2D8', N'1wJsjwDpO1JjcK4kvgqhdjJr103ZQiVLi', N'1D0Mhh9NXj-HRkpTa38aSL7wA3-0pN1Uy', N'PRACTICE', N'Word Skills', N'1H4idzWX5mHjvovnY5owY3q4EnvzGdNo3')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Reading', N'1DyFdOEY4ifVXZ3rfhibjCy9Das4kiimN', N'1zHB_I1rxXb8aphBxtHr4eqOcONN32kDT', N'1_AWAUBY_t-93F8CBiQ0A5mXeZDf_HL2D', N'PRACTICE', N'Reading', N'1dW2OmVp8jVkgeJzs4onN8TGZpc__IyfM')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Speaking', N'1cO6M5BAUEtfmT9RdkTRNosu_Ig10ojNd', N'1OvXs3umBVugNzcYIoW9HITO4q05aHgXe', N'16otFkUUP41RIrj6CPl1EYR0ISzk4R_NP', N'PRACTICE', N'Speaking', N'1PFI2tNqmaIvjYnfRYIf5RW0GwSVyh7H9')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Writing', N'1Uo6EYKjZaW3NxSAvP18S9-0nIY39u1Ob', N'1FSM9NXUeFpAV2qLDdQ78vfc-GSLJWTCW', N'1gdSzlTGvUw2IpB-Zo8K6NLdK1sXqCPnz', N'PRACTICE', N'Writing', N'1sHn-ad-JNkW6M2sxLcgTIrZboLCrOxpX')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Culture', N'1H8BZAH6CVSp5SyM9vLv8YagfITychC7L', N'16mKlVd4-sO4sPBwa-wzokysOfWqOdPSk', N'11eFjr5fOihzU07zWhV4FWmpJrX1f0WGL', N'PRACTICE', N'Culture', N'1HHQv-lCZV_gToT7Y4kUGt5vXvkUWJZSf')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (15, N'Review Unit 2', N'1DrqNbOCWsoXQII1cSFX2MjwiDMAcIZJg', N'1beTLOlo5j1a2IFR7JeK3gCiolC-WYA0C', N'1xLorg6RJSXj7A7MityWFD78r3mpq33nS', N'EXAM', N'Review Unit 2', N'1AParINplV_PgB49L8lAhxEXIm0L0ry4X')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Vocabulary', N'1fbyczr7J-vEcyZHnH88DhH2NkZXWtDlU', N'1n2yVkKij8PmdhDaoirlUG0wui0RtPdlU', N'1ssc8tFIiUZYr7K2ehPyiOKLbn8_DFqpB', N'PRACTICE', N'Vocabulary', N'1BApp7nqqeGWi58WuCxuRtZydYVC0E2EX')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Grammar 1', N'1eswP9eZhpbfbdyoS95crLlhHlO_FYEs-', N'1QUlhucIq7QFAUcOiWOHW426mw_-V7NAq', N'1F48DVOHed4H3eoX2BP91591CbavWelKw', N'PRACTICE', N'Grammar 1', N'1a_dMqgMBvWm87Z-Tuk6RMCdaw1lDrDU6')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Listening', N'1DpEWlIY9OgM1035LmASl0EzuBjdHdyOG', N'1NnpaAOH6SHxs4zJGfk7fJmGBX1KkhyIh', N'1W9-2UA9Mw9JK-E79sB3h6G3N4Ko02c8c', N'PRACTICE', N'Listening', N'12SQRv4gdQyIxcaf-_nGzl_lEDre4H5_R')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Grammar 2', N'1JIjXhxtIxIoTj9tjK2TRZxResI8CTi1S', N'1VWzM2g-D6O-_k25oiMnQpaQMP9DksAdX', N'1EaEBekEgo197k7JXYlD6PYtcKZ1OSeHK', N'PRACTICE', N'Grammar 2', N'1W2T4f83ctPkI-euNp-Btt3leMigHdaks')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Word Skills', N'1hfH012JTcplzN-NCwkyGG4wGEa_MUlK_', N'1DiQUD6NnZGM7hPlzp4BEQDAeo_zvqEOw', N'1s1XKoYQYxFAq9JO2CJxc1cHItIs-_Q6x', N'PRACTICE', N'Word Skills', N'1D8GDLcR2_5nU8UNPbjDS2ycLWJ2W5wat')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Reading', N'194Cq-ZsdxqixesNREFpkyhrrjZ_R1d1c', N'1vIUVDan51zWIHDK68Sx5xZLKtUPv9a8i', N'1p8dpZ5VcpjoyrdOn71MRg4e_-bTuhXLw', N'PRACTICE', N'Reading', N'1BEgUD4pSkj3aQrvT1gRIeKTnDJt7k1c9')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Speaking', N'1SRvtJNXmLJOsW05LwXr1ElZN-r0G9p5l', N'1YeCidhARt7mCvNFCNj-yfNuzbAsCFUcJ', N'100Y_cIWGeGs_B_UvTrO5ppwo6yIwH65-', N'PRACTICE', N'Speaking', N'1_sg3mxmwW6yFyaMGaiurqr7Wf98xCL8Y')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Writing', N'1S3Cv2o3WDohAG1OKDA2PQTP-Bq7PdUP9', N'1M8Tf_EGayj2EpogrE1cnQ5XoCvzHXqnN', N'1FB3HdCyPZtor5Jn8H9xdancwKAe-rBJN', N'PRACTICE', N'Writing', N'1-Zj-HObBJZwcVnAOyN8lyMJsrrMSydnN')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Culture', N'1rQd66QwAdrn-9Ir942aXkqf2qfO1ipIw', N'1SpM6_IuotEYyS_7nWgWsqB4-FsO12H3t', N'1U6AkHvHnd-ZWPHDnncwFVmNhrxMvvlvP', N'PRACTICE', N'Culture', N'1eGc2UjGKPS_aLcSQSqHbppgRPNGZMga7')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (16, N'Review Chapter 3', N'1kdeRVmlioFEZ8Za5eLo8iY8DsAZx1-eh', N'1UTs45LnS-qRAphZlk2OxL8Wn8LxJ4spz', N'1vgLZ2d5pdoNdSeNvgXR94NOg7IubI23b', N'PRACTICE', N'Review Chapter 3', N'1FcnGg37pbz8RKE1pCpIRkEBxSF9_jjYG')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Vocabulary', N'1NlPA1YJVx18IUgluImHXxtF2LExbdN0G', N'1E4y39NmNCNxCKOcUUmhtGdj-c-GS-1Ba', N'17n6J3wVvRmip-Kfty0wz-1ijOACY-ol9', N'PRACTICE', N'Vocabulary', N'16r7WkrOU84ysur9CYfvNH8EUDw7fjp3o')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Grammar 1', N'1t9RQs9SdkrFckwwph9oIcUgQJcby2M6y', N'1n1leHwLnZeAi_Ou7a00vTWJQVO1TEHh6', N'16vrmi54b8RmgvkrOsQkj_q5lntowXiUw', N'PRACTICE', N'Grammar 1', N'1pVW8jY56MxZzXOfLBNeNmIqVDqw1fEtZ')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Listening', N'1x7jDvFBJjYOLA4Wn3PNQvjuVnt9fjzbQ', N'12KoXxWX-o0OOLi7kD9tazUpH64WjarfP', N'1hRLeJ0DHTEUN45Ru2XfUhebtQzjJSl23', N'PRACTICE', N'Listening', N'1PsPMW1wrHCf4azyUPYHgY7TdVwa0Oc1X')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Grammar 2', N'1w28JK8Fkg0HSSfF5PW_1dUrO1txM9krA', N'1dRCwW-MuIoxx3iUUjen0nGDOClyk-YtA', N'1kO8qCj0PbB4KvK8WIMyLITVBlqaFS4d1', N'PRACTICE', N'Grammar 2', N'1pMwJPKxAdaYuAfdK0K04gk98DDX7bGAN')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Word Skills', N'1JAQdFTzLGjBXjTFIC2ywPHZD5Si3-VXW', N'1_JkMXe9MEyWkjnFgTWSqEOCKHAxhoouE', N'1bIi9ajE5UNp_PBZ2Unsd8inYwXqnbLeH', N'PRACTICE', N'Word Skills', N'1Q1wDgNioNiRo1u13ff9qFxDxD7C9bien')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Reading', N'1bnrKL61iyvOSkI9nzzTluXgFomzw9H60', N'1ZHM1YnTFOI64huHF3R9As7_9WcU08-Kc', N'1XHYhTbmrie0iH3YecuCcACt5_m7MGjDy', N'PRACTICE', N'Reading', N'1VZK_h7v2SFl3VMbZxNVocV8T-Jw_mbmS')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Speaking', N'19q69MtcsQ15XEIe0sKl18_opf71kI-t_', N'1UYCqoECWnY08D9iafOYZpsBKc0Gw5Iky', N'1wbIdSJT3vF1iKmswxEiqX3c6l9DXOn8E', N'PRACTICE', N'Speaking', N'1HQInKWY5rGGktE6hOwqwb-RWkTFFCNCf')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Writing', N'1_ny_XnLbH83EyTNtnWFyOU4BqGloaVyc', N'11ilTsq7h1TnGXEtt2gr2ADK2Cd2KmYX3', N'1s2cjccAQoKyZ09aPyGHDOY6Y7u5spO-g', N'PRACTICE', N'Writing', N'18tSnRiRyVYIDqhGtQvood0th1lJV7baB')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Culture', N'1-PoMGKjTfodwzVhl2AIenXUTwzgVYuD6', N'1igoIo0ZtnzSwElckT9cmlGwrDIK_E113', N'1pdB8fStizT9Wb-TBfRU-27SIWbevXyKB', N'PRACTICE', N'Culture', N'1yCmqN1ECwxLbXfTtpTrEqUsSIR7KaA9b')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (17, N'Review Unit 4', N'1Uckbzf0_-gTmI-AbNv5WBlxbqoz-RbBa', N'1bYVVDls8xNicM_1XknM8JJrB9yM5Dayf', N'1wZM3qLd7XhsPM3wFucIP9U6G_ZbC964D', N'EXAM', N'Review Unit 4', N'1-GQ-cOpMvCM-hzeJfR-tgQ4oO-wqnSv2')
GO


---Insert lesson to chapter in another courses
INSERT [dbo].[lessons] ([chapter_id],[description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (18, N'This lesson introduces the fundamental trigonometric values of an angle, including sine, cosine, tangent, and cotangent. Students will learn how to determine these values for different angles and apply them in solving mathematical problems.', N'1HJvmv_NYBQjBbZ5ALoz462tHUSjkGGQv', N'1DYRTmSN9cpBRK-P2biVrK9PjhxPGlCgU', N'1o7JalNBrZNlV4H2BTQXsduyqU6_nYTL7', N'PRACTICE', N'Trigonometric Values of an Angle', N'1AvWbUEhTxW7O5U1ow14qmN1iSaa_ZK5-')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (19, N'The lesson content will help students understand the concept of increasing and decreasing functions, as well as the conditions for a function to be monotonic over a given interval.', N'1xXSemFMFA16NK9JhUKBoorbzFGU8mXzb', N'14e1FN9fZj661WrTWiPyTuVGHyVDzmflP', N'1cs0h7qsFOSvL_sw8a22nM4Tq8NmynMGL', N'PRACTICE', N'The increasing and decreasing behavior of functions', N'13XZy0pXpge82Wo1HvxDPyMyClAPoIBvw')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (20, N'Overview of Vietnamese Literature from the Beginning of the August Revolution in 1945 to the 20th Century', N'1aP0ohXLLPsMakaBt0KalyWcPjknS3ZsN', N'14is_RTe7owFKpk8Dqp_DRNBlFtaqb3jK', N'1foRqNB1ll09GMPg8336P55wrafTZN_fT', N'PRACTICE', N'Overview of Vietnamese Literature from the Beginning of the August Revolution in 1945 to the 20th Century', N'1sLDqZpbaMTwp1TJOyoa7vmUV5R44KTx8')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (21, N'Overview of Vietnamese Literature from the Beginning of the August Revolution in 1945 to the 20th Century', N'1SdfcHFVjykCn-DeEWYCBKM4YOPBAgs_w', N'1rDM3sb6EJRCR_EIkRV670BMb4IXTQLxV', N'1tEGP3RSPxyfErS9-60gcWWgwb1zYd-TM', N'PRACTICE', N'Overview of Vietnamese Literature from the Beginning of the August Revolution in 1945 to the 20th Century', N'1obdCB2-hxcL-49kMs7XbtIfWQNT3LrUW')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (22, N'Getting Started', N'1odUqhQ9EnfY1Wrz-KkiuXuLBfqjDEBYA', N'1LtesybD1QANiF7HfVrkWfFQiFtOQq_e5', N'1cmMHFa8gE1KYBOtihuSmfpVW2D5RZ4pr', N'PRACTICE', N'Getting Started', N'1YIuIhRtK3pHVXvLlDiZViyQmPp46eQGj')
GO
INSERT [dbo].[lessons] ([chapter_id], [description], [document_folder_link], [lesson_drive_link], [quiz_image_link], [quiz_type], [title], [video_folder_link]) VALUES (23, N'Help students practice the skill of guessing meanings through context and reading for detailed information.', N'19fiUSCq9rbVNG9ikwwx7cObGYmyNfAlb', N'1q-_Oq6aNd6A8qCMiuoIDhWl9LjlYEd6r', N'1d8Q5ltOL2mqqQcs-ifyf405ntskO5iSq', N'PRACTICE', N'Home life - Reading', N'19cVTRqghYn_Pl7tsJDySA5dN9luyud2h')
GO


---Insert pdfs in lesson in math10
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (1, N'https://drive.google.com/file/d/1MV4Gm0NzgAF0DfRbNKAWBfYWxk3t3zez/preview', N'Propositions')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (2, N'https://drive.google.com/file/d/14SbRGPFY-G_sEGNr2cZ8gEskw0mRB0rX/preview', N'Sets and Set Operations')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (4, N'https://drive.google.com/file/d/1-sdALVa9bJWGtyZ_aarIoaSFLKonCR9X/preview', N'First-Degree Inequalities with Two Variables')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (5, N'https://drive.google.com/file/d/1k5jB3TuNQlvFZfZZmuRCbpHPXN2a4X6L/preview', N'Systems of First-Degree Inequalities with Two Variables')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (7, N'https://drive.google.com/file/d/1AVXTqMLsz65RP_HrNA_7Fn4HJ5lgA7Xw/preview', N'Trigonometric Values of an Angle from 0° to 180°')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (8, N'https://drive.google.com/file/d/1GbieeDBCApRuCFGyEtgsI_RhezHSS0dE/preview', N'Trigonometric Identities in Triangles')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (10, N'https://drive.google.com/file/d/1kt2q9-LV8DcR0EB6966VnS-60DuNXYmk/preview', N' Introduction to Vectors')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (11, N'https://drive.google.com/file/d/1HHiwYvgFC0RwWPzjWI9aG8NwomCyUO_2/preview', N'Sum and Difference of Two Vectors')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (12, N'https://drive.google.com/file/d/1eJPy4UA6zqcvDcjsNTme-Dp2AbLxYedG/preview', N'Scalar Multiplication of a Vector')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (13, N'https://drive.google.com/file/d/1qXUgkyY1PxAt8LRt0LQp7EwiW_CIvMlH/preview', N'Vectors in Coordinate Planes')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (14, N'https://drive.google.com/file/d/1QyhBfHI1XjvtuCOdkXjhwt-EXmfYfI68/preview', N'Dot Product of Two Vectors')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (16, N'https://drive.google.com/file/d/1Ie7YMDvOE-ZbKHDaPBtonLyU96SpnpdY/preview', N'Approximate Numbers and Errors')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (17, N'https://drive.google.com/file/d/1BP8O0rnml5cOUW5UPtzai4quwQMSPpKD/preview', N'Central Tendency Measures')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (18, N'https://drive.google.com/file/d/1WLp53xOVuGdI8YaQazbfcNG0Tt98m-B7/preview', N'Dispersion Measures')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (20, N'https://drive.google.com/file/d/1SwuQBNz7yzhcsvK9u06Epnp4R0Upp1vR/preview', N'Functions')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (21, N'https://drive.google.com/file/d/1BQ8nNLaVpDPUEslkag6_Gt6VUmWwNONq/preview', N'Quadratic Functions')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (22, N'https://drive.google.com/file/d/1CNe0slCGHJbqgmAJWEN5-Nf4WN3npgOk/preview', N'Quadratic Inequality Signs')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (23, N'https://drive.google.com/file/d/1Q782o901K8yhDIDIVJKn5VvAyvjeQCAo/preview', N'Quadratic and Rational Equations')
GO


--- Insert pdfs in lesson in liter10
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (25, N'https://drive.google.com/file/d/1tbwuXvYZFq4JZdPeAyUZkmHYbg63hvIX/preview', N'Myths of the Creation of the World')
GO



--- Insert pdfs in lesson in eng10
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (61, N'https://drive.google.com/file/d/1r-fvdTqB2gnBmpCvviXb-sWPnXC-jPhQ/preview', N'Introduction - Vocabulary 1')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (62, N'https://drive.google.com/file/d/1UE_TdrH4AdVsV6KN0B9s1xVZB6DFPBO6/preview', N'Introduction - Grammar 1')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (63, N'https://drive.google.com/file/d/15W0n4Wr45_ff__5PpfvWD9AtliUWRkbT/preview', N'Introduction - Vocabulary 2')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (64, N'https://drive.google.com/file/d/1vE_ieFNvQhKkDsDjoqdasBbPQXZo28wo/preview', N'Introduction - Grammar 2')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (66, N'https://drive.google.com/file/d/17DIxIvVCXWjCNXnLp-uB-jnB6ILsLKIA/preview', N'Vocabulary')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (67, N'https://drive.google.com/file/d/182WxnDZ04CgvXNzK3K2YyvvHOIgAUF8Y/preview', N'Grammar 1')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (68, N'https://drive.google.com/file/d/1ehi9kiqVODHwZ7gcizxYUEs2XOnDi6NR/preview', N'Listening')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (69, N'https://drive.google.com/file/d/1Ux2U_p43cJUeFczBti1uyvt_Bh5OUHH3/preview', N'Grammar 2')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (70,N'https://drive.google.com/file/d/1ZcrG1YAaFfYeJAAtLf2-vJEsXOiuoCFx/preview', N'Word Skills')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (71,N'https://drive.google.com/file/d/1O4L4Cd5P3mbmdDyIiDbd7_rIxtjLPQ_D/preview', N'Reading')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (72,N'https://drive.google.com/file/d/19T3gD32FtzQ00P4zLkMzu0tsR9o0HVnj/preview', N'Speaking')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (73,N'https://drive.google.com/file/d/1ijRYoZ-hZb7SE2fBacn1RBNddsNbuvuB/preview', N'Writing')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (74,N'https://drive.google.com/file/d/1-PiwSVIBf7VYETP4AACAXCkZLwW_OiN4/preview', N'Culture')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (76,N'https://drive.google.com/file/d/1IYfPAXRKt5j7A8TaUWl1Iv61xVLjfdSk/preview', N'Vocabulary')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (77,N'https://drive.google.com/file/d/1wVR4670Z5YVOlWOtOionSIPBobVrzNJY/preview', N'Grammar 1')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (78,N'https://drive.google.com/file/d/1J7Q39G2rjo418N8s1n88lbX0qs7inCuG/preview', N'Listening')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (79,N'https://drive.google.com/file/d/1bh5OrPuy71TQF6JyGuoe6xt6847kJKNr/preview', N'Grammar 2')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (80,N'https://drive.google.com/file/d/1hKj58w2gXCxrQyi-KdYvJ9plzTzDs2TZ/preview', N'Word Skills')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (81,N'https://drive.google.com/file/d/1f-ekQuJECIKVigIjWHFhuiMVj7a8wfsE/preview', N'Reading')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (82,N'https://drive.google.com/file/d/1-p3udRXLNylHOMGnLwzov3ONX5AUXiha/preview', N'Speaking')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (83,N'https://drive.google.com/file/d/1A03YOFf6wdyEdZeEB3vepaKJi8R3wpgX/preview', N'Writing')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (84,N'https://drive.google.com/file/d/1kzV2rq4Xe3-EqbzJVRDkXRsSzWcl4MB7/preview', N'Culture')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (86,N'https://drive.google.com/file/d/1UumvcjD0ZJP5N9y1m9IxPOzK-T0Y5TWh/preview', N'Vocabulary')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (87,N'https://drive.google.com/file/d/1D_ggYYsobYbgPO-SRi-npxYNgvN82RZx/preview', N'Grammar 1	')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (88,N'https://drive.google.com/file/d/1-n9QQEeBEigKMpwWOkMfpvUMjoupfEVS/preview', N'Listening')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (89,N'https://drive.google.com/file/d/1VcaOJa9467-yNBV0lFjIZpD3MPkpH0lr/preview', N'Grammar 2')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (90,N'https://drive.google.com/file/d/1pG_kSfm9jf2Rfd75yv8Wgl5CixqY5ipo/preview', N'Word Skills')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (91,N'https://drive.google.com/file/d/1FaAWv-_TvQxw8-gfkKumDRg-brGaoC21/preview', N'Reading')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (92,N'https://drive.google.com/file/d/1gZPHYgNzGNU-A2O_QCF8r1pNqCs0qwjU/preview', N'Speaking')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (93,N'https://drive.google.com/file/d/1KrrqTSoh2-ebqSvsS2QiHytG8Lur3gOI/preview', N'Writing')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (94,N'https://drive.google.com/file/d/1Do4IK2BVkvh1MrHLXqZRvgAmsm4jERxi/preview', N'Culture')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (96,N'https://drive.google.com/file/d/1aqqQLczrNANNAW-CU4yXEjkw4xYi56th/preview', N'Vocabulary')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (97,N'https://drive.google.com/file/d/1a8GCLT95bxTq40JY6ml8nCCjf-gd8nJs/preview', N'Grammar 1')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (98,N'https://drive.google.com/file/d/1ywkfnY1Y5psmSaqBFuXvuH55q6sIzjuo/preview', N'Listening')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (99,N'https://drive.google.com/file/d/13Eu0cL4dO6vYPPJ3YVpnyr9NGY94Pfsa/preview', N'Grammar 2')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (100,N'https://drive.google.com/file/d/1VQ9FZI7hiTlRZCXTbVCuo2XhIrZDGpwN/preview', N'Word Skills')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (101,N'https://drive.google.com/file/d/1gx9Lt4-hrBIvZTEgBIDCYetZ-vs5Jh1l/preview', N'Reading')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (102,N'https://drive.google.com/file/d/1GV9rbVpSMdz7n287yaTTXw609gC4WqDm/preview', N'Speaking')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (103,N'https://drive.google.com/file/d/11H4J72pRv_hXApv61ZtLP1sdvcFb_Ygd/preview', N'Writing')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (104,N'https://drive.google.com/file/d/1z3iZNEf_YV5K5IKAHmZyFdBjxhdqYChV/preview', N'Culture')
GO


--- Insert pdfs to lesson in another courses
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (106,N'https://drive.google.com/file/d/14aVLqa170Srp5unjCfC037LnEsxYI-Bn/preview', N'Trigonometric Values of an Angle')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (107,N'https://drive.google.com/file/d/1O1_nq6QrH78BdIWLoD1b3Mv8l33w6ULa/preview', N'The increasing and decreasing behavior of functions')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (108,N'https://drive.google.com/file/d/1LHG7J-OGSkDS5oioVapT6Sk4RZnRWrWn/preview', N'Overview of Vietnamese Literature from the Beginning of the August Revolution in 1945 to the 20th Century')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (109,N'https://drive.google.com/file/d/1MIucZzeGEYiMVtFB6rB-nciFk8o7rm4J/preview', N'Overview of Vietnamese Literature from the Beginning of the August Revolution in 1945 to the 20th Century')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (110,N'https://drive.google.com/file/d/1eV7-rfAfeWWJRcTcVxE_6FmBJltnYIfe/preview', N'Getting started')
GO
INSERT [dbo].[pdfs] ([lesson_id], [file_url], [title]) VALUES (111,N'https://drive.google.com/file/d/1_qfRyN9tiOTRUKX9R8X_Iv6ifwXOsvUg/preview', N'Home life - Reading')
GO


--- Insert video to lesson in math10
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (1, N'https://drive.google.com/file/d/1n4BeDp7w4CXsotIsmLlSxVnFj2WKeNki/preview', N'Introduction to Course')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (1, N'https://drive.google.com/file/d/1dQ8aL8fqlh5jqENYudOD7WmQH-IZYNPK/preview', N'Propositions')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (2, N'https://drive.google.com/file/d/1oHT35Kh4clXkbclNnPIDGD-hSmQrv8h2/preview', N'Sets and Set Operations')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (4, N'https://drive.google.com/file/d/10ITaUpkcZQaWhOHgtgBi4NgA78NFMp9i/preview', N'First-Degree Inequalities with Two Variables')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (5, N'https://drive.google.com/file/d/1tP8Ax-scm2AG7xCqMWKPViGMJCNdUQ8e/preview', N'Systems of First-Degree Inequalities with Two Variables')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (7, N'https://drive.google.com/file/d/195f3B3keUhDh-sdgoX6oW2XiFhGYsnrY/preview', N'Trigonometric Values of an Angle from 0° to 180°')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (8, N'https://drive.google.com/file/d/14Lg0RQS_M-8if9ShtVDwX2b7JNu6vod0/preview', N'Trigonometric Identities in Triangles')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (10, N'https://drive.google.com/file/d/1IktF5v91e5I0jZ8nC5v_UJiAKn6ai9XM/preview', N'Introduction to Vectors')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (11, N'https://drive.google.com/file/d/1gMtKkHOr71nj9tLjEuZkPGxE8-TZmDsw/preview', N'Sum and Difference of Two Vectors')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (12, N'https://drive.google.com/file/d/1SUaF2_otPQAeo5TO7JTSeT5kdC4cjq_Z/preview', N'Scalar Multiplication of a Vector')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (13, N'https://drive.google.com/file/d/1JHavBv7959nfBdPJukMTZKeNy97rLUv9/preview', N'Vectors in Coordinate Planes')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (14, N'https://drive.google.com/file/d/1El_EDAG7TUD102zpwiryQI5bOsMOo9PM/preview', N'Dot Product of Two Vectors')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (16, N'https://drive.google.com/file/d/19AV_ktKwGNcAc7Q7iCxdlEqzQ0L_0cr1/preview', N'Approximate Numbers and Errors')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (17, N'https://drive.google.com/file/d/1nLp-7INpv6xFWGOvwTvuWKIPFsEF8Mh9/preview', N'Central Tendency Measures')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (18, N'https://drive.google.com/file/d/1CwYMjiXxf63S060mXwQjHhASlRoD0gT3/preview', N'Dispersion Measures')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (20, N'https://drive.google.com/file/d/1rO8DXPwT4X5oj-Qc-URQZzkCSO6Ikjhi/preview', N'Functions')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (21, N'https://drive.google.com/file/d/1rrG8amhff7JCtOuRwsSo_V_i4deDcLzF/preview', N'Quadratic Functions')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (22, N'https://drive.google.com/file/d/10ykJ8HtwzqDc5EEUoXRS8QY9oTfURBjH/preview', N'Quadratic Inequality Signs')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (23, N'https://drive.google.com/file/d/1VViPgOq8ulZNIOg64UKaGMKSlgsKzHzH/preview', N'Quadratic and Rational Equations')
GO


--- Insert video to lesson in liter10
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (25, N'https://drive.google.com/file/d/1_OCI_ybkSHHsyhq_1BENqF673r4smsCq/preview', N'Myths of the Creation of the World')
GO


--- Insert video to lesson in eng10
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (61, N'https://drive.google.com/file/d/19xxSqdlbcXN68Z54wWjsTWOssKMBDKfo/preview', N'Introduction - Vocabulary 1')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (62, N'https://drive.google.com/file/d/1o79j_aOr05kepKlysHHjl4kXOESjkx_U/preview', N'Introduction - Grammar 1')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (63, N'https://drive.google.com/file/d/1yay5YbeXF0d9quqs12S2qVMR9NI3NvWC/preview', N'Introduction - Vocabulary 2')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (64, N'https://drive.google.com/file/d/1jteNI5faQSSBA1bk5UcGniZYVWhmLqF8/preview', N'Introduction - Grammar 2')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (66, N'https://drive.google.com/file/d/1pqCzo7rQh6EV-maj6jGJ-JJ2ysdmVp1a/preview', N'Vocabulary')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (67, N'https://drive.google.com/file/d/1-we-cJRH6isedLEKHV7E73Dzl1EMqIam/preview', N'Grammar 1')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (68, N'https://drive.google.com/file/d/1WbqEDc26woptVpTcIc2AzzlXLDooKD3V/preview', N'Listening')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (69, N'https://drive.google.com/file/d/1NDgboo80EW8SaihE8yWte_sywngvy7iH/preview', N'Grammar 2')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (70, N'https://drive.google.com/file/d/1s2wsaO0sI7Pp-RvFPRmpJk98EuuMlKW7/preview', N'Word Skills')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (71, N'https://drive.google.com/file/d/1GtaUK55B8XnmC4JPzjErRFDXIJrk-tFW/preview', N'Reading')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (72, N'https://drive.google.com/file/d/1zGxzPk-YTyGc_pdNIF4zqTA3QcgBkO75/preview', N'Speaking')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (73, N'https://drive.google.com/file/d/1psfW0z49j1Z7atnUtVlTW67r9qVpfXLK/preview', N'Writing')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (74, N'https://drive.google.com/file/d/1iq-3OUVnqJ0ccIBzyhqqVAMNFJOf_ReV/preview', N'Culture')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (76, N'https://drive.google.com/file/d/1ZazIThtpaIZ7EliHBT9bAwJn0WhlX9W8/preview', N'Vocabulary')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (77, N'https://drive.google.com/file/d/18xZN-YyIN6XwJnyENjZLVxeMbwXAliIC/preview', N'Grammar 1')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (78, N'https://drive.google.com/file/d/1vP9nPoTKMa4QurTkuag12tPWSHwq6oX8/preview', N'Listening')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (79, N'https://drive.google.com/file/d/1L-sYei_eiLuE-foo0zdt7OlAxrD5kPPI/preview', N'Grammar 2')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (80, N'https://drive.google.com/file/d/1y10qbcdgWVMnQ3u2_7JE8KD695y_hQdn/preview', N'Word Skills')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (81, N'https://drive.google.com/file/d/1PGuzscYyJ6EZBy8TXhaKxAe0gDcXqTVn/preview', N'Reading')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (82, N'https://drive.google.com/file/d/1ryiFnL-a8ftKas9pf7o2JusgATVNYVOk/preview', N'Speaking')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (83, N'https://drive.google.com/file/d/170_2Qpy2numtv2enfUU_5hUlXGl94qGb/preview', N'Writing')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (84, N'https://drive.google.com/file/d/11XtB-atbKKzgYYx4TmOAaR0m0AAjXh0J/preview', N'Culture')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (86, N'https://drive.google.com/file/d/101e_Vhz-Z2aFU-2oMt51ZTXEi5Nb0EC4/preview', N'Vocabulary')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (87, N'https://drive.google.com/file/d/1HnA19exBr1e_4MlM9cV-IP4MHOW-o6ad/preview', N'Grammar 1	')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (88, N'https://drive.google.com/file/d/1-grFLTYiB5XiBR9LQLFTRs4NsEA3Q4CN/preview', N'Listening')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (89, N'https://drive.google.com/file/d/11AtOQfTD8asfFF1hZOAoAvYsZoMAu7on/preview', N'Grammar 2')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (90, N'https://drive.google.com/file/d/1FGk40DmsXOFvJJwqf_0hmpSCKbCwFZNU/preview', N'Word Skills')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (91, N'https://drive.google.com/file/d/1HlFAoX6T_o0eeUJ-YzS1gHFFd2D3lrYY/preview', N'Reading')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (92, N'https://drive.google.com/file/d/1FTY8Omt9l7sgpuD386hh4e6AN63CaWBK/preview', N'Speaking')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (93, N'https://drive.google.com/file/d/1Qteh0ts_I13FT4uPP4Y5-4SU-o705SHR/preview', N'Writing')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (94, N'https://drive.google.com/file/d/1HB9ZU79RrwizQ3fk0RHU5HRH-70fzWyB/preview', N'Culture')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (96, N'https://drive.google.com/file/d/1ocO5X_1XKpDkWDdhD_eAg6YQxA-rqh8k/preview', N'Vocabulary')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (97, N'https://drive.google.com/file/d/1H9ua3hqI6z38-ajfF654WYYQdaghNNkR/preview', N'Grammar 1')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (98, N'https://drive.google.com/file/d/1xpwI2iXZbRkls_rjQg25OVmlYIZuGsuM/preview', N'Listening')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (99, N'https://drive.google.com/file/d/1zei_49zQH2pCAWAoSiffZ1sNxMHT2CB0/preview', N'Grammar 2')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (100, N'https://drive.google.com/file/d/1OnVwFzmGQ4M3b69OW-WGqzJ_s3RK9-bZ/preview', N'Culture')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (101, N'https://drive.google.com/file/d/1iBZBsSM8iYzH3kKEGaYIx2bYIKcWlNML/preview', N'Reading')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (102, N'https://drive.google.com/file/d/1hiqByQSYGbFHiD7zf7LjfmOJATZd_BfH/preview', N'Speaking')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (103, N'https://drive.google.com/file/d/1U4x1P5uRlA4sroRre1Uxgcx1vyBSg5MO/preview', N'Writing')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (104, N'https://drive.google.com/file/d/1S_LLYIxr7QWISZev36qaYJ2BWn0088hu/preview', N'Culture')
GO


---Insert video to lesson in another courses
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (106, N'https://drive.google.com/file/d/1I7q9X8ZZMC_lzinOloCi5EBv9QYGIx0s/preview', N'Introduction to Course')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (106, N'https://drive.google.com/file/d/1nLQxoE3jI4dt4bENYQ0KliiN-wGQBDQy/preview', N'Trigonometric Values of an Angle')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (107, N'https://drive.google.com/file/d/1tFFbuoB2zOPB7R-1rYzCMNtlO0V4DRsw/preview', N'Introduction to Course')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (107, N'https://drive.google.com/file/d/1aX20RdWGFtR7i-pCUiw8VlhQxP8Np4OU/preview', N'The increasing and decreasing behavior of functions')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (108, N'https://drive.google.com/file/d/13vFcqqznpCjtNwUMfyWgaD3mevJVxipd/preview', N'Introduction to Course')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (109, N'https://drive.google.com/file/d/10uhWqKhiVf_CKKd8R5_E4s5GOXtp7pxq/preview', N'Introduction to Course')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (109, N'https://drive.google.com/file/d/1O5wllc3Kc__dh_MJsl8LGeio8ba5wP03/preview', N'Overview of Vietnamese Literature from the Beginning of the August Revolution in 1945 to the 20th Century')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (110, N'https://drive.google.com/file/d/1lbI-tZR1wGpeiHg_YM7af16JaAXaXeJ1/preview', N'Getting started')
GO
INSERT [dbo].[videos] ([lesson_id], [file_url], [title]) VALUES (111, N'https://drive.google.com/file/d/14bAvQEx6CkBAbyKJtXEAc1NkRGRBMSAL/preview', N'Introduction to Course')
GO


-- Quiz for Math10
INSERT INTO [dbo].[quizzes]  
           ([lesson_id]  
           ,[time_limit]  
           ,[total_questions]  
           ,[quiz_title])  
VALUES  
           (1, 10, 10, 'Quiz No 1'),  --1
           (1, 10, 10, 'Quiz No 2'),  --2
           (1, 10, 10, 'Quiz No 3'),  --3
		   (2, 10, 10, 'Quiz No 1'),  --4
           (2, 10, 10, 'Quiz No 2'),  --5
           (2, 10, 10, 'Quiz No 3'),  --6
		   (3, 50, 40, 'Exam 1'),     --7

		   (4, 10, 10, 'Quiz No 1'),  --8
           (4, 10, 10, 'Quiz No 2'),  --9
           (4, 10, 10, 'Quiz No 3'),  --10
		   (5, 10, 10, 'Quiz No 1'),  --11
           (5, 10, 10, 'Quiz No 2'),  --12
           (5, 10, 10, 'Quiz No 3'),  --13
		   (6, 50, 40, 'Exam 2'),     --14

		   (7, 10, 10, 'Quiz No 1'),  --15
           (7, 10, 10, 'Quiz No 2'),  --16
           (7, 10, 10, 'Quiz No 3'),  --17
		   (8, 10, 10, 'Quiz No 1'),  --18
           (8, 10, 10, 'Quiz No 2'),  --19
           (8, 10, 10, 'Quiz No 3'),  --20
		   (9, 50, 40, 'Exam 3'),     --21

		   (10, 10, 10, 'Quiz No 1'),  --22
		   (11, 10, 10, 'Quiz No 1'),  --23
		   (12, 10, 10, 'Quiz No 1'),  --24
		   (13, 10, 10, 'Quiz No 1'),  
		   (14, 10, 10, 'Quiz No 1'),  
		   (15, 50, 40, 'Exam 4'),--27

		   (16, 10, 10, 'Quiz No 1'),
		   (17, 10, 10, 'Quiz No 1'),  
		   (18, 10, 10, 'Quiz No 1'),  
		   (19, 50, 40, 'Exam 5'),--31

		   (20, 10, 10, 'Quiz No 1'),
		   (21, 10, 10, 'Quiz No 1'),  
		   (22, 10, 10, 'Quiz No 1'),
		   (23, 10, 10, 'Quiz No 1'),  
		   (24, 50, 40, 'Exam 6');--36

-- For Lit10		   
INSERT INTO [dbo].[quizzes]  
           ([lesson_id]  
           ,[time_limit]  
           ,[total_questions]  
           ,[quiz_title])  
VALUES  
           (25, 10, 10, 'Quiz No 1'),  --1
           (26, 10, 10, 'Quiz No 1'),  --1
           (31, 10, 10, 'Quiz No 1'),  --1
           (32, 10, 10, 'Quiz No 1'),  
		   (38, 10, 10, 'Quiz No 1'),  --1
		   (39, 10, 10, 'Quiz No 1'),  --
		   (44, 10, 10, 'Quiz No 1'),  --1
		   (49, 10, 10, 'Quiz No 1'),  --
		   (54, 10, 10, 'Quiz No 1');  --1


-- For Eng10		   
INSERT INTO [dbo].[quizzes]  
           ([lesson_id]  
           ,[time_limit]  
           ,[total_questions]  
           ,[quiz_title])  
VALUES  
           (61, 10, 10, 'Quiz No 1'),  --1
           (65, 10, 10, 'Quiz No 1'),  --1
           (66, 10, 10, 'Quiz No 1'),  --1
           (67, 10, 10, 'Quiz No 1'),  
		   (68, 10, 10, 'Quiz No 1'),  --1
		   (76, 10, 10, 'Quiz No 1'),  --
		   (77, 10, 10, 'Quiz No 1'),  --1
		   (78, 10, 10, 'Quiz No 1'),  --
		   (86, 10, 10, 'Quiz No 1'),  --1
		   (87, 10, 10, 'Quiz No 1'),  --1
		   (88, 10, 10, 'Quiz No 1'),  --
		   (96, 10, 10, 'Quiz No 1'),
		   (97, 10, 10, 'Quiz No 1');


-- For Others		   
INSERT INTO [dbo].[quizzes]  
           ([lesson_id]  
           ,[time_limit]  
           ,[total_questions]  
           ,[quiz_title])  
VALUES  
           (106, 10, 10, 'Quiz No 1'),  --1
           (107, 10, 10, 'Quiz No 1'),  --1
           (108, 10, 10, 'Quiz No 1'),  --1
           (109, 10, 10, 'Quiz No 1'),
		   (110, 10, 10, 'Quiz No 1'),  --1
           (111, 10, 10, 'Quiz No 1')  



		   INSERT INTO [dbo].[question_bank]
           ([content]
           ,[correct_option]
           ,[option_1]
           ,[option_2]
           ,[option_3]
           ,[option_4])
VALUES
-- Quiz 1 (1-10)
           (N'Set A = {1, 2, 3, 4, 5}, Set B = {3, 4, 5, 6, 7}. What is A ∩ B?'
           ,N'{3, 4, 5}'
           ,N'{1, 2, 3}'
           ,N'{3, 4, 5}'
           ,N'{1, 2, 6, 7}'
           ,N'{1, 2, 3, 4, 5, 6, 7}'),

           (N'Set A = {a, b, c}, Set B = {c, d, e}. What is A ∪ B?'
           ,N'{a, b, c, d, e}'
           ,N'{a, b, c}'
           ,N'{c, d, e}'
           ,N'{a, b, c, d, e}'
           ,N'{a, b}'),

           (N'Given universal set U = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10} and set A = {2, 4, 6, 8, 10}, what is the complement of A in U?'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{1, 2, 3, 4, 5}'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{2, 4, 6, 8, 10}'
           ,N'{3, 5, 7, 9}'),

           (N'What is the correct notation for the empty set?'
           ,N'∅'
           ,N'{}'
           ,N'∅'
           ,N'0'
           ,N'∞'),

           (N'Given set A = {1, 3, 5, 7} and set B = {2, 4, 6, 8}, what is A ∩ B?'
           ,N'∅'
           ,N'{1, 3, 5}'
           ,N'{2, 4, 6}'
           ,N'∅'
           ,N'{1, 2, 3, 4, 5, 6, 7, 8}'),

           (N'What is the correct notation for the union of two sets A and B?'
           ,N'A ∪ B'
           ,N'A ∩ B'
           ,N'A ∪ B'
           ,N'A - B'
           ,N'B - A'),

           (N'What is the result of the intersection of two disjoint sets?'
           ,N'∅'
           ,N'Empty set (∅)'
           ,N'Set A'
           ,N'Set B'
           ,N'Universal set (U)'),

           (N'Given set A = {1, 2, 3, 4} and set B = {3, 4, 5, 6}, what is A - B?'
           ,N'{1, 2}'
           ,N'{3, 4}'
           ,N'{1, 2}'
           ,N'{5, 6}'
           ,N'{1, 2, 5, 6}'),

           (N'If A ⊆ B, which statement is true?'
           ,N'All elements of A belong to B'
           ,N'A and B are unrelated'
           ,N'All elements of A belong to B'
           ,N'A and B have no common elements'
           ,N'B is a subset of A'),

           (N'Set A = {x | x is an even number less than 10}. What is A?'
           ,N'{2, 4, 6, 8}'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{2, 4, 6, 8}'
           ,N'{0, 2, 4, 6, 8, 10}'
           ,N'{3, 6, 9}'),
-- Quiz 2 (11-20)
		   (N'If A = {2, 4, 6} and B = {4, 6, 8}, what is A ∩ B?'
           ,N'{4, 6}'
           ,N'{2, 4, 6}'
           ,N'{4, 6}'
           ,N'{2, 8}'
           ,N'{2, 4, 6, 8}'),

           (N'If A = {1, 2, 3, 4} and B = {3, 4, 5, 6}, what is A ∪ B?'
           ,N'{1, 2, 3, 4, 5, 6}'
           ,N'{3, 4}'
           ,N'{1, 2, 3, 4, 5, 6}'
           ,N'{5, 6}'
           ,N'{1, 2}'),

           (N'Set A = {x | x is a prime number less than 10}. What is A?'
           ,N'{2, 3, 5, 7}'
           ,N'{1, 3, 5, 7}'
           ,N'{2, 3, 5, 7}'
           ,N'{2, 4, 6, 8}'
           ,N'{3, 5, 9}'),

           (N'If A = {2, 3, 4} and B = {4, 5, 6}, what is A - B?'
           ,N'{2, 3}'
           ,N'{2, 3}'
           ,N'{4, 5}'
           ,N'{2, 3, 4, 5, 6}'
           ,N'{5, 6}'),

           (N'What is the complement of the universal set U?'
           ,N'∅'
           ,N'Universal set itself'
           ,N'∅'
           ,N'All real numbers'
           ,N'Set A'),

           (N'If A and B are disjoint sets, what is A ∩ B?'
           ,N'∅'
           ,N'{A, B}'
           ,N'∅'
           ,N'A'
           ,N'B'),

           (N'What is the symbol for subset?'
           ,N'⊆'
           ,N'∩'
           ,N'∪'
           ,N'⊆'
           ,N'⊂'),

           (N'What is the symbol for proper subset?'
           ,N'⊂'
           ,N'⊆'
           ,N'∩'
           ,N'∪'
           ,N'⊄'),

           (N'If A ⊆ B and B ⊆ A, what can we conclude?'
           ,N'A = B'
           ,N'A ⊂ B'
           ,N'B ⊂ A'
           ,N'A = B'
           ,N'A ∩ B = ∅'),

           (N'What is the universal set?'
           ,N'The set containing all elements under consideration'
           ,N'The set containing no elements'
           ,N'The set containing all elements under consideration'
           ,N'A set with only even numbers'
           ,N'A set with only odd numbers'),
-- Quiz 3 (21-30)
		   (N'If A = {1, 2, 3, 4} and B = {3, 4, 5, 6}, what is B - A?'
           ,N'{5, 6}'
           ,N'{1, 2}'
           ,N'{3, 4}'
           ,N'{5, 6}'
           ,N'{1, 2, 5, 6}'),

           (N'What is the correct notation for the complement of set A?'
           ,N'A′'
           ,N'A ∪ B'
           ,N'A ∩ B'
           ,N'A - B'
           ,N'A′'),

           (N'If A = {x | x is a multiple of 3}, which of the following is an element of A?'
           ,N'9'
           ,N'7'
           ,N'9'
           ,N'11'
           ,N'5'),

           (N'If A = {1, 2, 3, 4} and B = {3, 4, 5, 6}, what is A ∪ B - A ∩ B?'
           ,N'{1, 2, 5, 6}'
           ,N'{1, 2, 3}'
           ,N'{3, 4}'
           ,N'{1, 2, 5, 6}'
           ,N'{1, 2, 3, 4, 5, 6}'),

           (N'What is the power set of {a, b}?'
           ,N'{∅, {a}, {b}, {a, b}}'
           ,N'{a, b}'
           ,N'{∅, {a}, {b}, {a, b}}'
           ,N'{∅, {a}, {b}}'
           ,N'{{a, b}}'),

           (N'If A = {x | x is an odd number less than 10}, what is A?'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{2, 4, 6, 8}'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{3, 5, 7}'
           ,N'{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}'),

           (N'If A = {1, 2, 3} and B = {3, 4, 5}, what is A ∩ B ∪ A?'
           ,N'{1, 2, 3}'
           ,N'{1, 2, 3}'
           ,N'{3, 4, 5}'
           ,N'{1, 2, 3, 4, 5}'
           ,N'{∅}'),

           (N'If U = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10} and A = {2, 4, 6, 8, 10}, what is U - A?'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{2, 4, 6, 8, 10}'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{1, 2, 3, 4, 5}'
           ,N'{5, 6, 7, 8, 9}'),

           (N'If A = {2, 3, 5} and B = {3, 4, 6}, what is (A ∩ B) ∪ (A - B)?'
           ,N'{2, 3, 5}'
           ,N'{3, 4, 6}'
           ,N'{2, 3, 5}'
           ,N'{1, 2, 3, 4, 5}'
           ,N'{∅}'),

           (N'If P(A) is the power set of A = {1, 2}, how many elements does P(A) have?'
           ,N'4'
           ,N'2'
           ,N'3'
           ,N'4'
           ,N'5'),

-- FINAL, Quiz 1 (31-70)
           (N'If A = {1, 2, 3, 4} and B = {3, 4, 5, 6}, what is A ∩ B?'
           ,N'{3, 4}'
           ,N'{1, 2}'
           ,N'{5, 6}'
           ,N'{3, 4}'
           ,N'{1, 2, 5, 6}'),

           (N'If A = {2, 4, 6, 8} and B = {1, 3, 5, 7}, what is A ∪ B?'
           ,N'{1, 2, 3, 4, 5, 6, 7, 8}'
           ,N'{2, 4, 6, 8}'
           ,N'{1, 3, 5, 7}'
           ,N'{∅}'
           ,N'{1, 2, 3, 4, 5, 6, 7, 8}'),

           (N'If U = {a, b, c, d, e} and A = {a, b}, what is A′?'
           ,N'{c, d, e}'
           ,N'{a, b}'
           ,N'{c, d, e}'
           ,N'{∅}'
           ,N'{U}'),

           (N'Which of the following is an empty set?'
           ,N'{x | x is a natural number less than 0}'
           ,N'{x | x is an even prime number}'
           ,N'{x | x is a natural number less than 0}'
           ,N'{x | x is a letter in "math"}'
           ,N'{x | x is a positive integer}'),

           (N'If P = {x | x is a vowel in the English alphabet}, what is P?'
           ,N'{a, e, i, o, u}'
           ,N'{a, b, c, d, e}'
           ,N'{a, e, i, o, u}'
           ,N'{a, e, i}'
           ,N'{∅}'),

           (N'What is the power set of {a, b}?'
           ,N'{∅, {a}, {b}, {a, b}}'
           ,N'{{a}, {b}}'
           ,N'{∅, {a}, {b}, {a, b}}'
           ,N'{{a, b}}'
           ,N'{{∅}}'),

           (N'If A = {1, 2, 3} and B = {3, 4, 5}, what is A - B?'
           ,N'{1, 2}'
           ,N'{3, 4, 5}'
           ,N'{1, 2}'
           ,N'{∅}'
           ,N'{1, 2, 3, 4, 5}'),

           (N'If A ⊆ B and B ⊆ C, then which of the following is true?'
           ,N'A ⊆ C'
           ,N'A ⊆ B'
           ,N'A ⊆ C'
           ,N'B ⊆ C'
           ,N'A = C'),

           (N'What is A ∩ (B ∪ C) if A = {1, 2}, B = {2, 3}, and C = {3, 4}?'
           ,N'{2}'
           ,N'{1, 2}'
           ,N'{2}'
           ,N'{3}'
           ,N'{∅}'),

           (N'What is the cardinality of the set {a, b, c, d, e}?'
           ,N'5'
           ,N'4'
           ,N'5'
           ,N'6'
           ,N'∅'),
           
           (N'If A = {x | x is an even number less than 10}, what is A?'
           ,N'{2, 4, 6, 8}'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{2, 4, 6, 8}'
           ,N'{∅}'
           ,N'{10}'),

           (N'Which statement is true about sets?'
           ,N'Two sets are equal if they have exactly the same elements'
           ,N'Sets are always infinite'
           ,N'Two sets are equal if they have the same number of elements'
           ,N'The order of elements in a set matters'
           ,N'Two sets are equal if their elements are arranged identically'),

           (N'What is the universal set U?'
           ,N'The set containing all elements under consideration'
           ,N'The set containing only numbers'
           ,N'The empty set'
           ,N'The set containing all prime numbers'
           ,N'The power set of any given set'),

           (N'What is A ∩ B if A = {apple, banana, cherry} and B = {banana, grape, orange}?'
           ,N'{banana}'
           ,N'{apple, banana}'
           ,N'{banana}'
           ,N'{grape, orange}'
           ,N'{∅}'),

           (N'If A = {1, 2, 3} and B = {3, 4, 5}, what is A ∪ B?'
           ,N'{1, 2, 3, 4, 5}'
           ,N'{3}'
           ,N'{1, 2, 3}'
           ,N'{3, 4, 5}'
           ,N'{1, 2, 3, 4, 5}'),

           (N'If A = {2, 3, 4} and B = {4, 5, 6}, what is A - B?'
           ,N'{2, 3}'
           ,N'{4}'
           ,N'{2, 3}'
           ,N'{5, 6}'
           ,N'{∅}'),

           (N'What is the complement of A in U, given U = {1, 2, 3, 4, 5} and A = {2, 4}?'
           ,N'{1, 3, 5}'
           ,N'{2, 4}'
           ,N'{1, 3, 5}'
           ,N'{∅}'
           ,N'{U}'),

           (N'Which of the following is a subset of {1, 2, 3}?'
           ,N'{1, 2}'
           ,N'{4, 5}'
           ,N'{1, 2}'
           ,N'{5, 6, 7}'
           ,N'{∅}'),

           (N'If A = {x | x is a prime number less than 10}, what is A?'
           ,N'{2, 3, 5, 7}'
           ,N'{1, 3, 5, 7}'
           ,N'{2, 3, 5, 7}'
           ,N'{2, 4, 6, 8}'
           ,N'{∅}'),
 
           (N'If A = {1, 2, 3, 4} and B = {3, 4, 5, 6}, what is A ∩ B?'
           ,N'{3, 4}'
           ,N'{1, 2}'
           ,N'{5, 6}'
           ,N'{3, 4}'
           ,N'{1, 2, 5, 6}'),

           (N'If A = {2, 4, 6, 8} and B = {1, 3, 5, 7}, what is A ∪ B?'
           ,N'{1, 2, 3, 4, 5, 6, 7, 8}'
           ,N'{2, 4, 6, 8}'
           ,N'{1, 3, 5, 7}'
           ,N'{∅}'
           ,N'{1, 2, 3, 4, 5, 6, 7, 8}'),

           (N'If A = {1, 2, 3, 4} and B = {2, 3, 5, 6}, what is A ∩ B?'
           ,N'{2, 3}'
           ,N'{1, 4}'
           ,N'{5, 6}'
           ,N'{2, 3}'
           ,N'{∅}'),

           (N'If A = {x | x is an odd number less than 10}, what is A?'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{2, 4, 6, 8}'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{∅}'
           ,N'{10}'),

           (N'Which of the following is a proper subset of {a, b, c}?'
           ,N'{a, b}'
           ,N'{a, b, c}'
           ,N'{a, b}'
           ,N'{c, d}'
           ,N'{∅}'),

           (N'If U = {1, 2, 3, 4, 5, 6} and A = {2, 4, 6}, what is A′?'
           ,N'{1, 3, 5}'
           ,N'{2, 4, 6}'
           ,N'{1, 3, 5}'
           ,N'{∅}'
           ,N'{U}'),

           (N'If A = {a, b, c, d} and B = {c, d, e, f}, what is A - B?'
           ,N'{a, b}'
           ,N'{c, d}'
           ,N'{a, b}'
           ,N'{∅}'
           ,N'{a, b, e, f}'),

           (N'What is the cardinality of P({a, b})?'
           ,N'4'
           ,N'2'
           ,N'4'
           ,N'3'
           ,N'1'),

           (N'What is the Cartesian product of {1, 2} × {a, b}?'
           ,N'{(1,a), (1,b), (2,a), (2,b)}'
           ,N'{(1,1), (2,2)}'
           ,N'{(1,a), (1,b), (2,a), (2,b)}'
           ,N'{(a,1), (b,2)}'
           ,N'{∅}'),

           (N'What is A ∪ B if A = {1, 3} and B = {2, 4}?'
           ,N'{1, 2, 3, 4}'
           ,N'{1, 3}'
           ,N'{2, 4}'
           ,N'{1, 2, 3, 4}'
           ,N'{∅}'),

           (N'If A ⊆ B and B ⊆ A, what can be concluded?'
           ,N'A = B'
           ,N'A ⊂ B'
           ,N'A = B'
           ,N'A ∪ B = ∅'
           ,N'B ⊂ A'),

           (N'If A = {2, 4, 6} and B = {1, 2, 3, 4, 5, 6}, which statement is correct?'
           ,N'A ⊆ B'
           ,N'A ⊂ B'
           ,N'A ⊆ B'
           ,N'B ⊆ A'
           ,N'A = B'),

           (N'If A = {1, 2, 3} and B = {3, 4, 5}, what is A ∩ B?'
           ,N'{3}'
           ,N'{1, 2}'
           ,N'{3}'
           ,N'{4, 5}'
           ,N'{∅}'),

           (N'Which of the following statements is correct?'
           ,N'∅ is a subset of every set'
           ,N'A ⊆ B implies B ⊆ A'
           ,N'∅ is a subset of every set'
           ,N'For every set A, A ⊆ ∅'
           ,N'A ⊂ A'),

           (N'If A = {x | x is a prime number less than 20}, what is A?'
           ,N'{2, 3, 5, 7, 11, 13, 17, 19}'
           ,N'{1, 2, 3, 5, 7, 9}'
           ,N'{2, 3, 5, 7, 11, 13, 17, 19}'
           ,N'{∅}'
           ,N'{20}'),

           (N'If A = {x | x is a letter in "MATH"}, what is A?'
           ,N'{M, A, T, H}'
           ,N'{M, A, T}'
           ,N'{M, A, T, H}'
           ,N'{∅}'
           ,N'{A, T, H}'),

           (N'If A = {2, 4, 6} and B = {4, 6, 8}, what is A ∪ B?'
           ,N'{2, 4, 6, 8}'
           ,N'{4, 6}'
           ,N'{2, 4, 6, 8}'
           ,N'{∅}'
           ,N'{2, 8}'),

           (N'What is A - B if A = {1, 2, 3, 4} and B = {2, 3, 5, 6}?'
           ,N'{1, 4}'
           ,N'{2, 3}'
           ,N'{1, 4}'
           ,N'{∅}'
           ,N'{5, 6}'),

           (N'If A = {1, 2, 3} and B = {3, 4, 5}, what is (A ∪ B)′ in U = {1, 2, 3, 4, 5, 6}?'
           ,N'{6}'
           ,N'{1, 2}'
           ,N'{6}'
           ,N'{4, 5}'
           ,N'{∅}'),

		   (N'If A = {x | x is an even number less than 10}, what is A?'
           ,N'{2, 4, 6, 8}'
           ,N'{1, 3, 5, 7, 9}'
           ,N'{2, 4, 6, 8}'
           ,N'{∅}'
           ,N'{10}'),

           (N'What is the power set of {a, b}?'
           ,N'{{}, {a}, {b}, {a, b}}'
           ,N'{{a}, {b}}'
           ,N'{{}, {a}, {b}, {a, b}}'
           ,N'{{a, b}}'
           ,N'{{∅}}');

-- Literature 10	(71-100)	   
INSERT INTO [dbo].[question_bank]  
           ([content], [correct_option], [option_1], [option_2], [option_3], [option_4])  
VALUES  
('Which literary work was written by William Shakespeare?', 'Hamlet', 'Pride and Prejudice', 'Hamlet', 'Moby-Dick', 'The Great Gatsby'),  
('Who is the author of "To Kill a Mockingbird"?', 'Harper Lee', 'Mark Twain', 'Harper Lee', 'Ernest Hemingway', 'F. Scott Fitzgerald'),  
('Which novel features the character Jay Gatsby?', 'The Great Gatsby', 'Moby-Dick', 'The Great Gatsby', 'Of Mice and Men', 'Jane Eyre'),  
('What is the main theme of "1984" by George Orwell?', 'Totalitarianism', 'Romance', 'Totalitarianism', 'Adventure', 'Comedy'),  
('Which poem starts with "Shall I compare thee to a summer’s day?"', 'Sonnet 18', 'The Raven', 'Sonnet 18', 'Daffodils', 'Ode to a Nightingale'),  
('Who wrote "Romeo and Juliet"?', 'William Shakespeare', 'Charles Dickens', 'William Shakespeare', 'Jane Austen', 'Leo Tolstoy'),  
('What is the genre of "Frankenstein" by Mary Shelley?', 'Gothic Fiction', 'Science Fiction', 'Gothic Fiction', 'Mystery', 'Historical Fiction'),  
('Which novel follows the journey of a young boy named Huckleberry Finn?', 'The Adventures of Huckleberry Finn', 'The Catcher in the Rye', 'The Adventures of Huckleberry Finn', 'Lord of the Flies', 'Wuthering Heights'),  
('Who wrote the epic poem "Paradise Lost"?', 'John Milton', 'Geoffrey Chaucer', 'John Milton', 'Edgar Allan Poe', 'Homer'),  
('Which of the following is a play by Arthur Miller?', 'The Crucible', 'Death of a Salesman', 'The Crucible', 'Othello', 'Macbeth'), 
('Who is the protagonist in the novel "Jane Eyre"?', 'Jane Eyre', 'Elizabeth Bennet', 'Jane Eyre', 'Hester Prynne', 'Catherine Earnshaw'),  
('Which literary movement is associated with Edgar Allan Poe?', 'Romanticism', 'Realism', 'Romanticism', 'Modernism', 'Naturalism'),  
('What is the setting of "Of Mice and Men" by John Steinbeck?', 'California', 'New York', 'California', 'Texas', 'Florida'),  
('Who wrote the novel "Pride and Prejudice"?', 'Jane Austen', 'Charlotte Brontë', 'Jane Austen', 'Emily Dickinson', 'George Eliot'),  
('What is the famous opening line of "A Tale of Two Cities"?', 'It was the best of times, it was the worst of times', 'Call me Ishmael', 'It was the best of times, it was the worst of times', 'All happy families are alike', 'Once upon a time'),  
('Which novel features the character Atticus Finch?', 'To Kill a Mockingbird', 'The Catcher in the Rye', 'To Kill a Mockingbird', '1984', 'Brave New World'),  
('Who wrote the dystopian novel "Brave New World"?', 'Aldous Huxley', 'George Orwell', 'Aldous Huxley', 'Ray Bradbury', 'Margaret Atwood'),  
('What is the central theme of "The Scarlet Letter"?', 'Sin and redemption', 'War and peace', 'Sin and redemption', 'Friendship and loyalty', 'Science and progress'),  
('Who wrote "The Picture of Dorian Gray"?', 'Oscar Wilde', 'Mary Shelley', 'Oscar Wilde', 'Franz Kafka', 'Virginia Woolf'),  
('Which Shakespearean play features the characters Rosencrantz and Guildenstern?', 'Hamlet', 'Macbeth', 'Hamlet', 'Othello', 'King Lear'), 
('Who is the tragic hero in the play "Macbeth"?', 'Macbeth', 'Hamlet', 'Macbeth', 'Othello', 'King Lear'),  
('Which literary device is used when an object represents a deeper meaning?', 'Symbolism', 'Metaphor', 'Symbolism', 'Irony', 'Alliteration'),  
('Who wrote the novel "Moby-Dick"?', 'Herman Melville', 'Nathaniel Hawthorne', 'Herman Melville', 'Mark Twain', 'Jules Verne'),  
('Which poem by Robert Frost contains the line "Two roads diverged in a yellow wood"?', 'The Road Not Taken', 'Stopping by Woods on a Snowy Evening', 'The Road Not Taken', 'Ode to a Nightingale', 'The Raven'),  
('Who is the main character in "The Catcher in the Rye"?', 'Holden Caulfield', 'Jay Gatsby', 'Holden Caulfield', 'Tom Sawyer', 'Heathcliff'),  
('Which novel is set in a dystopian future where books are banned?', 'Fahrenheit 451', '1984', 'Fahrenheit 451', 'Brave New World', 'The Handmaid''s Tale'),  
('Who wrote the Greek tragedy "Oedipus Rex"?', 'Sophocles', 'Euripides', 'Sophocles', 'Aristophanes', 'Homer'),  
('What is the main theme of "Animal Farm" by George Orwell?', 'Corruption of power', 'Love and sacrifice', 'Corruption of power', 'Survival and hope', 'War and peace'),  
('Which author is known for writing Gothic horror stories like "The Tell-Tale Heart"?', 'Edgar Allan Poe', 'H.P. Lovecraft', 'Edgar Allan Poe', 'Bram Stoker', 'Mary Shelley'),  
('In "Julius Caesar", who says the famous line "Et tu, Brute?"', 'Julius Caesar', 'Mark Antony', 'Julius Caesar', 'Cassius', 'Brutus');  



--English 10
INSERT INTO [dbo].[question_bank]  
           ([content], [correct_option], [option_1], [option_2], [option_3], [option_4])  
VALUES  
    ('What is the synonym of "huge"?', 'large', 'tiny', 'short', 'weak', 'large'),  
    ('Which word is the opposite of "difficult"?', 'easy', 'hard', 'complex', 'tough', 'easy'),  
    ('What is the past tense of "go"?', 'went', 'gone', 'going', 'goed', 'went'),  
    ('Which sentence is grammatically correct?', 'She has been studying for three hours.', 'She have been study for three hours.', 'She is been studying for three hours.', 'She was been studying for three hours.', 'She has been studying for three hours.'),  
    ('Choose the correct word: "I have never ____ such a beautiful sunset."', 'seen', 'saw', 'see', 'seeing', 'seen'),  
    ('What is the plural form of "child"?', 'children', 'childs', 'childes', 'childrens', 'children'),  
    ('Which word is a noun?', 'happiness', 'happy', 'happily', 'happier', 'happiness'),  
    ('What does the word "environment" refer to?', 'The surroundings in which people, animals, or plants live.', 'A type of weather.', 'A scientific theory.', 'A political movement.', 'The surroundings in which people, animals, or plants live.'),  
    ('Which of these is an irregular verb?', 'run', 'jump', 'walk', 'talk', 'run'),  
    ('Which sentence is in passive voice?', 'The book was written by J.K. Rowling.', 'J.K. Rowling wrote the book.', 'The book writes by J.K. Rowling.', 'J.K. Rowling is writing the book.', 'The book was written by J.K. Rowling.'),
	('What is the comparative form of "good"?', 'better', 'gooder', 'best', 'more good', 'better'),  
    ('Choose the correct word: "She is _____ than her brother."', 'taller', 'more tall', 'most tall', 'tallest', 'taller'),  
    ('Which of these is a preposition?', 'under', 'quickly', 'happy', 'sing', 'under'),  
    ('What is the correct order of adjectives in English?', 'Opinion, Size, Age, Color, Origin, Material, Purpose', 'Size, Opinion, Color, Age, Material, Origin, Purpose', 'Color, Size, Age, Opinion, Purpose, Material, Origin', 'Age, Color, Size, Opinion, Origin, Material, Purpose', 'Opinion, Size, Age, Color, Origin, Material, Purpose'),  
    ('What is the superlative form of "far"?', 'farthest', 'farther', 'more far', 'most far', 'farthest'),  
    ('Choose the correct verb tense: "By next year, she _____ in London for five years."', 'will have lived', 'will be living', 'has lived', 'lived', 'will have lived'),  
    ('Which of these is an example of a conditional sentence?', 'If it rains, we will stay at home.', 'She goes to school every day.', 'They are watching TV now.', 'I have finished my homework.', 'If it rains, we will stay at home.'),  
    ('What is the past participle of "write"?', 'written', 'wrote', 'writing', 'writes', 'written'),  
    ('Choose the correct sentence:', 'I have never been to Japan.', 'I never have been to Japan.', 'I has never been to Japan.', 'I am never been to Japan.', 'I have never been to Japan.'),
	('Which word is a synonym of "happy"?', 'joyful', 'sad', 'angry', 'tired', 'joyful'),  
    ('What is the plural form of "mouse"?', 'mice', 'mouses', 'mouse', 'mices', 'mice'),  
    ('Choose the correct sentence:', 'He has already finished his homework.', 'He have already finished his homework.', 'He is already finish his homework.', 'He was already finished his homework.', 'He has already finished his homework.'),  
    ('What is the past tense of "eat"?', 'ate', 'eaten', 'eating', 'eats', 'ate'),  
    ('Which of the following is a countable noun?', 'apple', 'water', 'sugar', 'air', 'apple'),  
    ('Which sentence is in the present perfect tense?', 'She has traveled to Paris.', 'She travels to Paris.', 'She is traveling to Paris.', 'She will travel to Paris.', 'She has traveled to Paris.'),  
    ('What does "bilingual" mean?', 'Able to speak two languages', 'Able to write poetry', 'A person who loves books', 'A type of music', 'Able to speak two languages'),  
    ('Which of these is an interrogative sentence?', 'Where do you live?', 'I live in New York.', 'She lives in New York.', 'They are from New York.', 'Where do you live?'),  
    ('What is the opposite of "increase"?', 'decrease', 'grow', 'expand', 'rise', 'decrease'),  
    ('Choose the correct preposition: "She is interested _____ learning new languages."', 'in', 'on', 'at', 'for', 'in'), 
	('Which word means "a person who writes books"?', 'author', 'painter', 'singer', 'actor', 'author');

-- For Others
INSERT INTO [dbo].[question_bank]  
           ([content], [correct_option], [option_1], [option_2], [option_3], [option_4])  
VALUES  
('Who was the first President of the United States?', 'George Washington', 'Abraham Lincoln', 'Thomas Jefferson', 'John Adams', 'George Washington'),  
('In which year did World War II end?', '1945', '1939', '1918', '1950', '1945'),  
('What is the capital of the Roman Empire?', 'Rome', 'Athens', 'Cairo', 'London', 'Rome'),  
('Which country first landed a human on the Moon?', 'USA', 'Russia', 'China', 'India', 'USA'),  
('What is the Olympic motto?', 'Faster, Higher, Stronger', 'Run, Jump, Win', 'Glory, Unity, Strength', 'Compete, Win, Honor', 'Faster, Higher, Stronger'),  
('Which sport is known as the "king of sports"?', 'Football (Soccer)', 'Basketball', 'Tennis', 'Baseball', 'Football (Soccer)'),  
('How many players are there in a standard soccer team?', '11', '9', '7', '13', '11'),  
('Which board game uses a checkered board and pieces that move diagonally?', 'Checkers', 'Chess', 'Go', 'Backgammon', 'Checkers'),  
('What is the objective of the game of Chess?', 'Checkmate the opponent’s king', 'Capture all opponent’s pieces', 'Move all pieces to the last row', 'Reach 100 points first', 'Checkmate the opponent’s king'),  
('Which of the following is a team sport?', 'Basketball', 'Swimming', 'Archery', 'Gymnastics', 'Basketball');  


INSERT INTO [dbo].[quiz_question]
           ([question_id]
           ,[quiz_id])
VALUES
			(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1), (9, 1), (10, 1),

			(11, 2), (12, 2), (13, 2), (14, 2), (15, 2), (16, 2), (17, 2), (18, 2), (19, 2), (20, 2),

		    (21, 3), (22, 3), (23, 3), (24, 3), (25, 3), (26, 3), (27, 3), (28, 3), (29, 3), (30, 3),

			(1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4), (7, 4), (8, 4), (9, 4), (10, 4),
			
			(11, 5), (12, 5), (13, 5), (14, 5), (15, 5), (16, 5), (17, 5), (18, 5), (19, 5), (20, 5),  

			(21, 6), (22, 6), (23, 6), (24, 6), (25, 6), (26, 6), (27, 6), (28, 6), (29, 6), (30, 6),

		    (31, 7), (32, 7), (33, 7), (34, 7), (35, 7), (36, 7), (37, 7), (38, 7), (39, 7), (40, 7), 
			(41, 7), (42, 7), (43, 7), (44, 7), (45, 7), (46, 7), (47, 7), (48, 7), (49, 7), (50, 7),  
			(51, 7), (52, 7), (53, 7), (54, 7), (55, 7), (56, 7), (57, 7), (58, 7), (59, 7), (60, 7),  
			(61, 7), (62, 7), (63, 7), (64, 7), (65, 7), (66, 7), (67, 7), (68, 7), (69, 7), (70, 7),

		    (1, 8), (2, 8), (3, 8), (4, 8), (5, 8), (6, 8), (7, 8), (8, 8), (9, 8), (10, 8),

			(11, 9), (12, 9), (13, 9), (14, 9), (15, 9), (16, 9), (17, 9), (18, 9), (19, 9), (20, 9),

			(21, 10), (22, 10), (23, 10), (24, 10), (25, 10), (26, 10), (27, 10), (28, 10), (29, 10), (30, 10),

			(1, 11), (2, 11), (3, 11), (4, 11), (5, 11), (6, 11), (7, 11), (8, 11), (9, 11), (10, 11),

			(11, 12), (12, 12), (13, 12), (14, 12), (15, 12), (16, 12), (17, 12), (18, 12), (19, 12), (20, 12),

			(21, 13), (22, 13), (23, 13), (24, 13), (25, 13), (26, 13), (27, 13), (28, 13), (29, 13), (30, 13),

			(31, 14), (32, 14), (33, 14), (34, 14), (35, 14), (36, 14), (37, 14), (38, 14), (39, 14), (40, 14),
			(41, 14), (42, 14), (43, 14), (44, 14), (45, 14), (46, 14), (47, 14), (48, 14), (49, 14), (50, 14),
			(51, 14), (52, 14), (53, 14), (54, 14), (55, 14), (56, 14), (57, 14), (58, 14), (59, 14), (60, 14),
			(61, 14), (62, 14), (63, 14), (64, 14), (65, 14), (66, 14), (67, 14), (68, 14), (69, 14), (70, 14),

			(1, 15), (2, 15), (3, 15), (4, 15), (5, 15), (6, 15), (7, 15), (8, 15), (9, 15), (10, 15),

			(11, 16), (12, 16), (13, 16), (14, 16), (15, 16), (16, 16), (17, 16), (18, 16), (19, 16), (20, 16),

			(21, 17), (22, 17), (23, 17), (24, 17), (25, 17), (26, 17), (27, 17), (28, 17), (29, 17), (30, 17),

			(1, 18), (2, 18), (3, 18), (4, 18), (5, 18), (6, 18), (7, 18), (8, 18), (9, 18), (10, 18),

			(11, 19), (12, 19), (13, 19), (14, 19), (15, 19), (16, 19), (17, 19), (18, 19), (19, 19), (20, 19),

			(21, 20), (22, 20), (23, 20), (24, 20), (25, 20), (26, 20), (27, 20), (28, 20), (29, 20), (30, 20),

			(31, 21), (32, 21), (33, 21), (34, 21), (35, 21), (36, 21), (37, 21), (38, 21), (39, 21), (40, 21),
			(41, 21), (42, 21), (43, 21), (44, 21), (45, 21), (46, 21), (47, 21), (48, 21), (49, 21), (50, 21),
			(51, 21), (52, 21), (53, 21), (54, 21), (55, 21), (56, 21), (57, 21), (58, 21), (59, 21), (60, 21),
			(61, 21), (62, 21), (63, 21), (64, 21), (65, 21), (66, 21), (67, 21), (68, 21), (69, 21), (70, 21),

			(1, 22), (2, 22), (3, 22), (4, 22), (5, 22), (6, 22), (7, 22), (8, 22), (9, 22), (10, 22),

			(11, 23), (12, 23), (13, 23), (14, 23), (15, 23), (16, 23), (17, 23), (18, 23), (19, 23), (20, 23),

			(21, 24), (22, 24), (23, 24), (24, 24), (25, 24), (26, 24), (27, 24), (28, 24), (29, 24), (30, 24),

			(1, 25), (2, 25), (3, 25), (4, 25), (5, 25), (6, 25), (7, 25), (8, 25), (9, 25), (10, 25),

			(11, 26), (12, 26), (13, 26), (14, 26), (15, 26), (16, 26), (17, 26), (18, 26), (19, 26), (20, 26),

			(31, 27), (32, 27), (33, 27), (34, 27), (35, 27), (36, 27), (37, 27), (38, 27), (39, 27), (40, 27),
			(41, 27), (42, 27), (43, 27), (44, 27), (45, 27), (46, 27), (47, 27), (48, 27), (49, 27), (50, 27),
			(51, 27), (52, 27), (53, 27), (54, 27), (55, 27), (56, 27), (57, 27), (58, 27), (59, 27), (60, 27),
			(61, 27), (62, 27), (63, 27), (64, 27), (65, 27), (66, 27), (67, 27), (68, 27), (69, 27), (70, 27),

			(11, 28), (12, 28), (13, 28), (14, 28), (15, 28), (16, 28), (17, 28), (18, 28), (19, 28), (20, 28),

			(21, 29), (22, 29), (23, 29), (24, 29), (25, 29), (26, 29), (27, 29), (28, 29), (29, 29), (30, 29),

			(1, 30), (2, 30), (3, 30), (4, 30), (5, 30), (6, 30), (7, 30), (8, 30), (9, 30), (10, 30),

			(31, 31), (32, 31), (33, 31), (34, 31), (35, 31), (36, 31), (37, 31), (38, 31), (39, 31), (40, 31),
			(41, 31), (42, 31), (43, 31), (44, 31), (45, 31), (46, 31), (47, 31), (48, 31), (49, 31), (50, 31),
			(51, 31), (52, 31), (53, 31), (54, 31), (55, 31), (56, 31), (57, 31), (58, 31), (59, 31), (60, 31),
			(61, 31), (62, 31), (63, 31), (64, 31), (65, 31), (66, 31), (67, 31), (68, 31), (69, 31), (70, 31),

			(11, 32), (12, 32), (13, 32), (14, 32), (15, 32), (16, 32), (17, 32), (18, 32), (19, 32), (20, 32),

			(21, 33), (22, 33), (23, 33), (24, 33), (25, 33), (26, 33), (27, 33), (28, 33), (29, 33), (30, 33),

			(1, 34), (2, 34), (3, 34), (4, 34), (5, 34), (6, 34), (7, 34), (8, 34), (9, 34), (10, 34),

			(11, 35), (12, 35), (13, 35), (14, 35), (15, 35), (16, 35), (17, 35), (18, 35), (19, 35), (20, 35),

			(31, 36), (32, 36), (33, 36), (34, 36), (35, 36), (36, 36), (37, 36), (38, 36), (39, 36), (40, 36),
			(41, 36), (42, 36), (43, 36), (44, 36), (45, 36), (46, 36), (47, 36), (48, 36), (49, 36), (50, 36),
			(51, 36), (52, 36), (53, 36), (54, 36), (55, 36), (56, 36), (57, 36), (58, 36), (59, 36), (60, 36),
			(61, 36), (62, 36), (63, 36), (64, 36), (65, 36), (66, 36), (67, 36), (68, 36), (69, 36), (70, 36);

GO

-- For Lit 10
INSERT INTO [dbo].[quiz_question]
           ([question_id]
           ,[quiz_id])
VALUES
			(71, 37), (72, 37), (73, 37), (74, 37), (75, 37), (76, 37), (77, 37), (78, 37), (79, 37), (80, 37),

			(81, 38), (82, 38), (83, 38), (84, 38), (85, 38), (86, 38), (87, 38), (88, 38), (89, 38), (90, 38),
			
			(91, 39), (92, 39), (93, 39), (94, 39), (95, 39), (96, 39), (97, 39), (98, 39), (99, 39), (100, 39),  

			(71, 40), (72, 40), (73, 40), (74, 40), (75, 40), (76, 40), (77, 40), (78, 40), (79, 40), (80, 40),  

			(81, 41), (82, 41), (83, 41), (84, 41), (85, 41), (86, 41), (87, 41), (88, 41), (89, 41), (90, 41),  

			(91, 42), (92, 42), (93, 42), (94, 42), (95, 42), (96, 42), (97, 42), (98, 42), (99, 42), (100, 42),
			
			(71, 43), (72, 43), (73, 43), (74, 43), (75, 43), (76, 43), (77, 43), (78, 43), (79, 43), (80, 43),  

			(81, 44), (82, 44), (83, 44), (84, 44), (85, 44), (86, 44), (87, 44), (88, 44), (89, 44), (90, 44),  

			(91, 45), (92, 45), (93, 45), (94, 45), (95, 45), (96, 45), (97, 45), (98, 45), (99, 45), (100, 45);  


-- For Eng 10
INSERT INTO [dbo].[quiz_question]
           ([question_id]
           ,[quiz_id])
VALUES
			(101, 46), (102, 46), (103, 46), (104, 46), (105, 46), (106, 46), (107, 46), (108, 46), (109, 46), (110, 46),

			(111, 47), (112, 47), (113, 47), (114, 47), (115, 47), (116, 47), (117, 47), (118, 47), (119, 47), (120, 47),

			(121, 48), (122, 48), (123, 48), (124, 48), (125, 48), (126, 48), (127, 48), (128, 48), (129, 48), (130, 48),

			(121, 49), (122, 49), (123, 49), (124, 49), (125, 49), (126, 49), (127, 49), (128, 49), (129, 49), (130, 49),

			(121, 50), (122, 50), (123, 50), (124, 50), (125, 50), (126, 50), (127, 50), (128, 50), (129, 50), (130, 50),

			(121, 51), (122, 51), (123, 51), (124, 51), (125, 51), (126, 51), (127, 51), (128, 51), (129, 51), (130, 51),

			(121, 52), (122, 52), (123, 52), (124, 52), (125, 52), (126, 52), (127, 52), (128, 52), (129, 52), (130, 52),

			(121, 53), (122, 53), (123, 53), (124, 53), (125, 53), (126, 53), (127, 53), (128, 53), (129, 53), (130, 53),

			(121, 54), (122, 54), (123, 54), (124, 54), (125, 54), (126, 54), (127, 54), (128, 54), (129, 54), (130, 54),

			(121, 55), (122, 55), (123, 55), (124, 55), (125, 55), (126, 55), (127, 55), (128, 55), (129, 55), (130, 55),

			(121, 56), (122, 56), (123, 56), (124, 56), (125, 56), (126, 56), (127, 56), (128, 56), (129, 56), (130, 56),

			(121, 57), (122, 57), (123, 57), (124, 57), (125, 57), (126, 57), (127, 57), (128, 57), (129, 57), (130, 57),

			(121, 58), (122, 58), (123, 58), (124, 58), (125, 58), (126, 58), (127, 58), (128, 58), (129, 58), (130, 58)


-- For Others
INSERT INTO [dbo].[quiz_question]
           ([question_id]
           ,[quiz_id])
VALUES
			(131, 59), (132, 59), (133, 59), (134, 59), (135, 59), (136, 59), (137, 59), (138, 59), (139, 59), (140, 59),
			(131, 60), (132, 60), (133, 60), (134, 60), (135, 60), (136, 60), (137, 60), (138, 60), (139, 60), (140, 60),
			(131, 61), (132, 61), (133, 61), (134, 61), (135, 61), (136, 61), (137, 61), (138, 61), (139, 61), (140, 61),
			(131, 62), (132, 62), (133, 62), (134, 62), (135, 62), (136, 62), (137, 62), (138, 62), (139, 62), (140, 62),
			(131, 63), (132, 63), (133, 63), (134, 63), (135, 63), (136, 63), (137, 63), (138, 63), (139, 63), (140, 63),
			(131, 64), (132, 64), (133, 64), (134, 64), (135, 64), (136, 64), (137, 64), (138, 64), (139, 64), (140, 64);


			INSERT INTO [dbo].[sliders] ([status], [created_at], [backlink], [description], [image_url], [title])  
VALUES  
    (1, GETDATE(), 'http://localhost:8080/course?keyword=&gradeId=-1&subjectId=1', N'Comprehensive Mathematics Course for High School Students', 'https://lh3.googleusercontent.com/d/1Q_Qtg9l17D343ixxzlLXw1SJRY-90l6r', N'Mathematics Mastery'),  
    (1, GETDATE(), 'http://localhost:8080/course?keyword=&gradeId=-1&subjectId=3', N'English Grammar and Writing Skills for Grade 10-12', 'https://lh3.googleusercontent.com/d/1MlsqYbcqlDSwoL-14wH4f_phvpzL-FFR', N'English Proficiency'),  
    (0, GETDATE(), 'http://localhost:8080/course?keyword=&gradeId=-1&subjectId=4', N'Advanced Physics Concepts for High School Students', 'https://lh3.googleusercontent.com/d/16Sp2MfaIxMN8IxeHVv70IgV513Svh5Hf', N'Physics Excellence'),  
    (1, GETDATE(), 'http://localhost:8080/course?keyword=&gradeId=-1&subjectId=5', N'Fundamentals of Chemistry: From Basics to Advanced', 'https://lh3.googleusercontent.com/d/1Mu8taHgyUBn30x4XCLew1rdiBTRmGqyx', N'Chemistry Essentials'),
	(1, GETDATE(), 'http://localhost:8080/course?keyword=&gradeId=-1&subjectId=2', N'Exploring Literature: Classic and Modern Masterpieces', 'https://lh3.googleusercontent.com/d/1D4iEAu9QuLKdpAkIcCEaYB4pniCht0a0', N'Literature Appreciation'),
	(1, GETDATE(), 'http://localhost:8080/course?keyword=&gradeId=-1&subjectId=6', N'Biology: Understanding Life and Its Processes', 'http://lh3.googleusercontent.com/d/1jQGe0MRg5fPrH173nR0N59zDPEh2zvAY', N'Biology Insights');  


	INSERT INTO posts (user_id, grade_id, subject_id, post_content, post_img_url, created_at, updated_at,reported) 
VALUES 
(6, 1,3, N'"Did you revised lesson carefully before tests tomorrow" teacher asked', NULL, '2024-03-05 14:30:15', '2024-03-05 14:30:15',0),
(1, 2,3, N'Write a short paragraph (120-150 words) about a time you did something independently. These suggestions may be helpful for you. - what did you do independently? - why did you decide to do it on your own? - what challenges did you face? -How did you feel afterward?', NULL, '2023-11-19 14:30:15', '2023-11-19 14:30:15',0),
(2, 3,3, N'Topic 2: Caring for those in need not?


1. Have you ever join voluntary activities? Why/why

2. Why do you like to volnteer?

3. What did you do to help disadvantaged people?

4. How do you feel when you help them?

Giúp mik bài này vs ạ tối mik nộp r :( ', NULL , '2024-03-15 14:30:15', '2024-03-15 14:30:15',0),
(3, 1,2, N'
Đợi mẹ

Em bé ngồi nhìn ra ruộng lúa
Trời tối trên đầu hè. Nửa vầng trăng non

Em bé nhìn vầng trăng, nhưng chưa nhìn thấy mẹ
Mẹ lẫn trên cánh đồng. Đồng lúa lẫn vào đêm

Ngọn lửa bếp chưa nhen. Căn nhà tranh trống trải
Đom đóm bay ngoài ao. Đom đóm đã vào nhà

Em bé nhìn đóm bay, chờ tiếng bàn chân mẹ
Bàn chân mẹ lội bùn ì oạp phía đồng xa

Trời về khuya lung linh trắng vườn hoa mận trắng
Mẹ đã bế vào nhà nỗi đợi vẫn nằm mơ

1.       (0,75 điểm) Hãy xác định thể thơ của bài "Đợi mẹ" và chỉ ra dấu hiệu để nhận biết thể thơ đó.

2.       (0,75 điểm) Tìm hình ảnh được so sánh với nỗi nhớ mẹ của đứa trẻ trong bài thơ?

3.       (1,0 điểm) Chỉ ra biện pháp tu từ được sử dụng trong câu thơ "Mẹ lâu về con càng mong mẹ" và nêu tác dụng của biện pháp tu từ đó?

4.       (1,0 điểm) Anh/chị hãy nhận xét về tình cảm của đứa trẻ dành cho mẹ trong bài thơ?

5.       (1,5 điểm) Nêu thông điệp mà tác giả muốn gửi gắm trong bài thơ "Đợi mẹ". Từ thông điệp đó, hãy viết một đoạn văn ngắn (khoảng 5-7 dòng) trình bày suy nghĩ của em về tình mẫu tử trong cuộc sống.', NULL, '2024-03-24 14:30:15', '2024-03-24 14:30:15',0),
(5, 1,1, N'Viết pttb của dt² đi qua A (1;-2) và có việc tơ chỉ phương u = (4:-3)', NULL, '2024-03-25 14:30:15', '2024-03-25 14:30:15',0),
(5, 1,4, N'Một máy bơm nước mỗi giây có thể bơm 15l nước lên bể cao 10m lấy g=10m/s2 tính công suất của mấy bơm', NULL, '2024-03-15 14:30:15', '2024-03-15 14:30:15',0), 
(1, 3,4, N'một hộp Sinh dùng bơm tay để bơm không khí vào 1 quả bóng cao su có thể tích là 3 lít với áp suất không khí là 10^5 N/m^2 xung quanh của bơm có chiều cao là 42cm đường kính xy lanh là 5cm biết trong quá trình bơm nhiệt độ không thay đổi?
có thể áp dụng định luật boyle cho quá trình biến đổi trạng thái này ?', NULL, '2025-02-08 14:30:15', '2025-02-08 14:30:15',0),
(7, 3,4, N'Một chất điểm dao động điều hòa, hãy tìm phát biểu đúng?

A. Cơ năng lớn nhất tại biên

B. Động năng cực đại khi vật ở vị trí biên

C. Thế năng cực tiểu khi tốc độ cực tiểu

D. Thế năng cực đại tại vị trí vận tốc đổi chiều', NULL, '2025-02-07 11:19:15', '2025-02-07 11:19:15',0),
(14, 3,4, NULL, 'https://lh3.googleusercontent.com/d/1rv7TDEIfuGCgrkbdjP5jBT_2C407UWuT','2025-01-15 01:19:15', '2025-01-15 02:19:15',0),
(13, 2,6, N'Câu 1. (1 điểm) Động vật có những hình thức phát triển nào? Lấy ví dụ cho mỗi hình thức phát triển đó.', NULL,'2025-01-10 01:19:15', '2025-01-10 02:19:15',0),
(16, 2,6, N'HIV có tấn công cơ quan cơ thể hay không', NULL,'2025-02-10 07:19:15', '2025-02-10 07:19:15',0),
(18, 3,1, N'Bài toán như sau: Hãy điền những chữ số thích hợp vào dạng định lý FLT dưới đây

Ax + By = Cz. Bằng điều kiện A, B, C, x, y, z đều là các số nguyên dương trong đó x, y, z lớn hơn 2 còn A, B, C có cùng bội số chung nhỏ nhất.', NULL,'2025-02-17 08:19:15', '2025-02-17 08:19:15',0);


INSERT INTO Comments (post_id, user_id, content, comment_date,like_count,reported) VALUES
(3, 1, N'1. I have join voluntary activities about 3 months ago. Because I think it is very interesting.
2. Because I can joined in outdoor activities.
3. I made a charity where everyone can donate some of their money to help the poor and disadvantaged people.
4. I feel very happy when I help them', '2025-03-16 04:30:15',1,1),
(4, 6, N'1. Thể thơ và dấu hiệu nhận biết (0,75 điểm)

Thể thơ: Thể thơ tự do.
Dấu hiệu nhận biết: Bài thơ không tuân theo một số lượng chữ cố định trong mỗi dòng, không có quy tắc về vần điệu và số dòng trong mỗi khổ.
2. Hình ảnh so sánh với nỗi nhớ mẹ (0,75 điểm)

Hình ảnh "nỗi đợi vẫn nằm mơ" được so sánh với nỗi nhớ mẹ của đứa trẻ. Điều này cho thấy, nỗi nhớ mẹ đã in sâu vào tâm trí, thậm chí đi vào giấc mơ của em bé.
3. Biện pháp tu từ và tác dụng (1,0 điểm)

Biện pháp tu từ: Điệp từ "mẹ".
Tác dụng:
Nhấn mạnh sự mong ngóng, chờ đợi của đứa trẻ dành cho mẹ.
Thể hiện tình cảm yêu thương, nhớ nhung da diết của em bé.
Tạo nhịp điệu cho câu thơ, tăng tính biểu cảm.
4. Nhận xét về tình cảm của đứa trẻ (1,0 điểm)

Tình cảm của đứa trẻ dành cho mẹ trong bài thơ là tình cảm yêu thương, nhớ nhung sâu sắc. Em bé mong ngóng mẹ từng phút giây, dõi theo từng dấu hiệu nhỏ nhất để tìm kiếm bóng dáng mẹ. Tình cảm ấy được thể hiện qua hành động ngồi đợi mẹ về trong đêm tối, qua việc em bé nhìn trăng, nhìn đom đóm và lắng nghe tiếng chân mẹ.
5. Thông điệp và suy nghĩ về tình mẫu tử (1,5 điểm)

Thông điệp:
Bài thơ thể hiện tình mẫu tử thiêng liêng, sâu sắc.
Ca ngợi sự hy sinh thầm lặng của người mẹ.
Gợi nhắc mỗi người hãy biết trân trọng những giây phút bên mẹ.
Đoạn văn về tình mẫu tử:
Tình mẫu tử là thứ tình cảm thiêng liêng và cao quý nhất trên đời. Đó là tình yêu thương vô bờ bến của người mẹ dành cho con cái. Mẹ sẵn sàng hy sinh tất cả để con được hạnh phúc, bình an. Tình mẹ bao la như biển Thái Bình, không gì có thể so sánh được. Mỗi chúng ta hãy biết trân trọng những giây phút được ở bên mẹ, hãy yêu thương và báo hiếu mẹ khi còn có thể.',  '2024-03-24 16:30:15',1,0),
(5, 4, N'- Gọi đường thẳng cần viết phương trình là d.

Vì đường thẳng (d) đi qua A(1; -2) và có vtcp là 
u
→
u
  (4; -3)

Suy ra phương trình tham số của đường thẳng (d) là:

{
x
=
1
+
4
t
y
=
−
2
−
3
t
{ 
x=1+4t
y=−2−3t
​
 

- Phương trình chính tắc của (d) là:

x
−
1
4
4
x−1
​
  = 
y
+
2
−
3
−3
y+2
​
 

- Phương trình tổng quát của (d) là:

-3x+3 - 4(y+2)= 0

<=> -3x-4y-5=0',  '2024-03-26 06:30:15',1,0),
(5, 7, N'[5%÷100%]×20=', '2024-03-26 08:30:15',0,0),
(6, 8, N'khối lượng nước bơm trong 1 giây:
m=V×D=0.015×1000=15 kg
công suất:
​
P
=
m
g
h
t
=
115
×
10
×
10
=
1500
W
=
1.5
k
W
P= 
t
mgh
​
 =115×10×10=1500W=1.5kW',  '2024-03-22 07:30:15',0,0),
(6, 9, N'1.5 kW',  '2024-03-22 06:30:15',0,0),
(7, 2, N'Quá trình bơm khí vào quả bóng làm tăng thể tích khí trong quả bóng, và áp suất của khí bên trong quả bóng có thể thay đổi.
Tuy nhiên, trong suốt quá trình này, nếu nhiệt độ không thay đổi (như bài toán đã nói), và khí trong quả bóng có thể coi là khí lý tưởng thì chúng ta có thể áp dụng định lý Boyle cho khí trong quả bóng, tức là:
FtrongVquả bóng=hằng số
Tuy nhiên, đối với khí bên ngoài (khí xung quanh trong không khí), chúng ta chỉ có thể coi áp suất môi trường là không đổi và không cần tính toán chi tiết áp suất của không khí bên ngoài khi bơm.
Vì vậy, có thể áp dụng định lý Boyle cho quá trình này, nhưng chỉ trong phạm vi khí trong quả bóng khi thể tích thay đổi, và áp suất thay đổi tương ứng với thể tích theo mối quan hệ PV=hằng số.
Lưu ý Trong thực tế, một số yếu tố như ma sát giữa không khí và thành bơm, cũng như sự không đồng nhất trong quá trình bơm khí có thể làm ảnh hưởng nhỏ đến sự áp dụng lý thuyết lý tưởng, nhưng trong khuôn khổ bài toán lý thuyết, áp dụng định lý Boyle là hợp lý.', '2025-02-08 23:30:15',1,1),
(8, 5, N'Chọn D . D. Thế năng cực đại tại vị trí vận tốc đổi chiều. - Vì: Khi vận tốc của vật đổi chiều thì lúc đó vật đang ở vị trí biên dương (hoặc biên âm) = > x = + − A =>x=+−A. Mà W t = 1 / 2 k x 2 W t ​ =1/2kx 2 = > W t = 1 / 2 k A 2 = W [ t ( m a x ) ] =>W t ​ =1/2kA 2 =W [ ​ t(max)]',  '2025-02-09 11:30:15',1,0),
(8, 7, N'giải thích kĩ hộ mk với nha','2025-02-09 15:20:15',0,0),
(10, 41, N'25 tháng 3
Động vật có các biểu thức phát triển sau:

Phát triển trực tiếp : Con non chung con trưởng thành ngay từ đầu, không có giai đoạn sôi sục.
Ví dụ: Con người, chó, mèo.
Phát triển Gián tiếp : Con non trải qua nhiều giai đoạn sôi sục khác trước khi trở
Phát triển hoàn toàn:hoàn toàn ( hoàn thiện : Con non trải qua các giai đoạn khác biệt hoàn toàn (trứng → sôi sục → nhộng → trưởng thành).
Ví dụ: Bướm,cá.
Phát triển không hoàn toàn : Con không giống trưởng thành nhưng chưa phát triển đầy đủ, chỉ cần di chuyển xác thực để hoàn thiện.
Ví dụ: Cào cào, châu Phi.thành trưởng thành.
Ví dụ: Ếch, cá.',  '2025-01-11 02:19:15',0,0),
(11, 5, N'Không',  '2025-01-11 03:19:15',0,0),
(12, 9, N'A, B, C có bội số chung nhỏ nhất là 6

Các bước giải:

A= 1, B= 2, B=3

x= 8, y=5, z=3

Ax + By = Cz = 1 x 8 + 2 x 5 = 3 x 6

A, B, C có bội số chung nhỏ nhất là 6.

Chúc bạn học tốt!',  '2025-02-17 14:19:15',0,0);


INSERT INTO users_courses (user_id, course_id, date, rating, comment) 
VALUES 
(1, 1, '2025-03-20', 4, N'Good'),
(2, 1, '2025-03-26', 3, N'Ok'),
(3, 2, '2025-03-20', 5, N'Excellent'),
(4, 2, '2025-03-26', 2, N'Not great'),
(5, 3, '2025-03-26', 3, N'Normal'),
(6, 3, '2025-03-20', 4, N'Good'),
(7, 4, '2025-03-26', 3, N'Normal'),
(8, 4, '2025-03-26', 5, N'Excellent'),
(9, 5, '2025-03-20', 4, N'Good'),
(10, 4, '2025-03-26', 4, N'Good'),
(11, 7, '2025-03-26', 2, N'Needs improvement'),
(12, 7, '2025-03-26', 3, N'Normal'),
(13, 8, '2025-03-26', 4, N'Good'),
(14, 8, '2025-03-20', 3, N'Average'),
(15, 9, '2025-03-26', 4, N'Good'),
(16, 9, '2025-03-26', 5, N'Great'),
(17, 11, '2025-03-26', 4, N'Good'),
(18, 11, '2025-03-12', 3, N'Normal'),
(19, 11, '2025-03-20', 5, N'Excellent'),
(20, 11, '2025-03-21', 4, N'Good'),
(1, 3, '2025-03-26', 3, N'Normal'),
(2, 4, '2025-03-26', 4, N'Good'),
(3, 5, '2025-03-26', 4, N'Good'),
(4, 5, '2025-03-26', 3, N'Ok'),
(5, 7, '2025-03-26', 5, N'Excellent'),
(6, 8, '2025-03-26', 4, N'Good'),
(7, 9, '2025-03-26', 3, N'Normal'),
(8, 1, '2025-03-26', 5, N'Excellent'),
(9, 11, '2025-03-26', 2, N'Needs improvement'),
(10, 12, '2025-03-26', 4, N'Good'),
(6, 1, '2025-03-20', 4, N'Good'),
(7, 1, '2025-03-21', 3, N'Ok'),
(9, 1, '2025-03-23', 2, N'Needs improvement'),
(10, 1, '2025-03-24', 4, N'Good'),
(11, 1, '2025-03-25', 3, N'Normal'),
(12, 1, '2025-03-26', 5, N'Great'),
(13, 1, '2025-03-19', 4, N'Good'),
(14, 1, '2025-03-18', 3, N'Ok'),
(15, 2, '2025-03-17', 5, N'Excellent'),
(16, 3, '2025-03-16', 4, N'Good'),
(17, 3, '2025-03-15', 2, N'Could be better'),
(18, 4, '2025-03-14', 3, N'Normal'),
(19, 4, '2025-03-13', 5, N'Great'),
(20, 5, '2025-03-12', 4, N'Good'),
(21, 1, '2024-12-01', 4, N'Good'),-- gói hết hạn vẫn cho comment khóa đã từng học 
(22, 2, '2024-12-01', 4, N'Good'),-- gói hết hạn vẫn cho comment khóa đã từng học 
(23, 3, '2024-12-01', 4, N'Good'),-- gói hết hạn vẫn cho comment khóa đã từng học 
(24, 4, '2024-12-01', 4, N'Good'),-- gói hết hạn vẫn cho comment khóa đã từng học 
(25, 5, '2024-12-01', 4, N'Good');-- gói hết hạn vẫn cho comment khóa đã từng học 


INSERT INTO [user_membership] ([user_id], [vip_package_id], [registration_date], [expiration_date], [status], [paid_price]) 
VALUES
-- Gói đã hết hạn 
(18, 2, '2024-03-27', '2025-03-27', 'EXPIRED', 1600000),   -- để check tự động downgrade từ vip student về student
(19, 2, '2024-03-27', '2025-03-27', 'EXPIRED', 500000),   -- để check tự động downgrade từ vip student về student
(20, 2, '2024-03-27', '2025-03-27', 'EXPIRED', 900000),  -- để check tự động downgrade từ vip student về student
(21, 2, '2024-03-05', '2025-03-05', 'EXPIRED', 900000), -- gói hết hạn vẫn cho comment khóa đã từng học 
(22, 2, '2024-03-05', '2025-03-05', 'EXPIRED', 900000), -- gói hết hạn vẫn cho comment khóa đã từng học 
(23, 2, '2024-03-05', '2025-03-05', 'EXPIRED', 900000), -- gói hết hạn vẫn cho comment khóa đã từng học 
(24, 2, '2024-03-05', '2025-03-05', 'EXPIRED', 900000), -- gói hết hạn vẫn cho comment khóa đã từng học 
(25, 2, '2024-03-05', '2025-03-05', 'EXPIRED', 900000),-- gói hết hạn vẫn cho comment khóa đã từng học 

-- gói còn hạn 
(1, 1, '2025-02-01', '2025-08-01', 'ACTIVE', 500000),  
(2, 2, '2025-03-01', '2026-03-01', 'ACTIVE', 900000),  
(3, 3, '2025-04-01', '2027-04-01', 'ACTIVE', 1600000),  
(4, 1, '2025-01-15', '2025-07-15', 'ACTIVE', 500000),  
(5, 2, '2025-02-20', '2026-02-20', 'ACTIVE', 900000),  
(6, 3, '2025-03-10', '2027-03-10', 'ACTIVE', 1600000),  
(7, 1, '2025-04-05', '2025-10-05', 'ACTIVE', 500000),  
(8, 2, '2025-05-01', '2026-05-01', 'ACTIVE', 900000),  
(9, 3, '2025-06-01', '2027-06-01', 'ACTIVE', 1600000),  
(10, 1, '2025-07-01', '2026-01-01', 'ACTIVE', 500000),  
(11, 2, '2025-08-01', '2026-08-01', 'ACTIVE', 900000),  
(12, 3, '2025-09-01', '2027-09-01', 'ACTIVE', 1600000),  
(13, 1, '2025-10-01', '2026-04-01', 'ACTIVE', 500000),  
(14, 2, '2025-11-01', '2026-11-01', 'ACTIVE', 900000),  
(15, 3, '2025-12-01', '2027-12-01', 'ACTIVE', 1600000),  
(16, 1, '2026-01-01', '2026-07-01', 'ACTIVE', 500000),  
(17, 2, '2026-02-01', '2027-02-01', 'ACTIVE', 900000);  


-- INSERT quiz_results
INSERT INTO quiz_result (max_score, num_atempts, quiz_id, user_id, result_status)
VALUES (9, 2, 1, 1, 'Pass'),  --uerId = 1
      (7, 1, 2, 1, 'Fail'),
      (8, 3, 3, 1, 'Pass'),
      (5, 2, 4, 1, 'Fail'),
      (10, 1, 5, 1, 'Pass'),
      (6, 3, 6, 1, 'Fail'),
      (9, 2, 7, 1, 'Pass'),
      (4, 1, 8, 1, 'Fail'),
      (7, 2, 9, 1, 'Fail'),
      (10, 1, 10, 1, 'Pass'),
      (3, 3, 11, 1, 'Fail'),
      (8, 2, 12, 1, 'Pass'),
      (6, 1, 13, 1, 'Fail'),
      (9, 3, 14, 1, 'Pass'),
      (5, 2, 15, 1, 'Fail'),
      (7, 1, 16, 1, 'Fail'),
      (8, 3, 17, 1, 'Pass'),
      (10, 2, 18, 1, 'Pass'),
      (4, 1, 19, 1, 'Fail'),
      (9, 3, 20, 1, 'Pass'),
      (6, 2, 21, 1, 'Fail'),
      (7, 1, 22, 1, 'Fail'),
      (8, 3, 23, 1, 'Pass'),
      (10, 2, 24, 1, 'Pass'),
      (5, 1, 25, 1, 'Fail'),
      (9, 3, 26, 1, 'Pass'),
      (6, 2, 27, 1, 'Fail'),
      (7, 1, 28, 1, 'Fail'),
      (8, 3, 29, 1, 'Pass'),
      (10, 2, 30, 1, 'Pass'),
      (4, 1, 31, 1, 'Fail'),
      (9, 3, 32, 1, 'Pass'),
      (6, 2, 33, 1, 'Fail'),
      (7, 1, 34, 1, 'Fail'),
      (8, 3, 35, 1, 'Pass'),
      (10, 2, 36, 1, 'Pass'),


      (8, 1, 1, 2, 'Pass'),  -- UserID = 2 pass hết
      (9, 2, 2, 2, 'Pass'),
      (10, 3, 3, 2, 'Pass'),
      (9, 1, 4, 2, 'Pass'),
      (8, 2, 5, 2, 'Pass'),
      (10, 3, 6, 2, 'Pass'),
      (9, 1, 7, 2, 'Pass'),
      (8, 2, 8, 2, 'Pass'),
      (10, 3, 9, 2, 'Pass'),
      (9, 1, 10, 2, 'Pass'),
      (8, 2, 11, 2, 'Pass'),
      (10, 3, 12, 2, 'Pass'),
      (9, 1, 13, 2, 'Pass'),
      (8, 2, 14, 2, 'Pass'),
      (10, 3, 15, 2, 'Pass'),
      (9, 1, 16, 2, 'Pass'),
      (8, 2, 17, 2, 'Pass'),
      (10, 3, 18, 2, 'Pass'),
      (9, 1, 19, 2, 'Pass'),
      (8, 2, 20, 2, 'Pass'),
      (10, 3, 21, 2, 'Pass'),
      (9, 1, 22, 2, 'Pass'),
      (8, 2, 23, 2, 'Pass'),
      (10, 3, 24, 2, 'Pass'),
      (9, 1, 25, 2, 'Pass'),
      (8, 2, 26, 2, 'Pass'),
      (10, 3, 27, 2, 'Pass'),
      (9, 1, 28, 2, 'Pass'),
      (8, 2, 29, 2, 'Pass'),
      (10, 3, 30, 2, 'Pass'),
      (9, 1, 31, 2, 'Pass'),
      (8, 2, 32, 2, 'Pass'),
      (10, 3, 33, 2, 'Pass'),
      (9, 1, 34, 2, 'Pass'),
      (8, 2, 35, 2, 'Pass'),
      (10, 3, 36, 2, 'Pass'),


      (6, 2, 1, 6, 'Fail'),  -- User 6 (quiz_id từ 1-12)
      (9, 1, 2, 6, 'Pass'),
      (7, 3, 3, 6, 'Fail'),
      (10, 2, 4, 6, 'Pass'),
      (8, 1, 5, 6, 'Pass'),
      (5, 3, 6, 6, 'Fail'),
      (9, 2, 7, 6, 'Pass'),
      (3, 1, 8, 6, 'Fail'),
      (10, 2, 9, 6, 'Pass'),
      (7, 1, 10, 6, 'Fail'),
      (8, 3, 11, 6, 'Pass'),
      (4, 2, 12, 6, 'Fail'),


      (7, 2, 1, 7, 'Fail'),  -- User 7 (quiz_id từ 1-16)
      (10, 1, 2, 7, 'Pass'),
      (6, 3, 3, 7, 'Fail'),
      (9, 2, 4, 7, 'Pass'),
      (8, 1, 5, 7, 'Pass'),
      (4, 3, 6, 7, 'Fail'),
      (9, 2, 7, 7, 'Pass'),
      (2, 1, 8, 7, 'Fail'),
      (10, 2, 9, 7, 'Pass'),
      (7, 1, 10, 7, 'Fail'),
      (8, 3, 11, 7, 'Pass'),
      (3, 2, 12, 7, 'Fail'),
      (9, 1, 13, 7, 'Pass'),
      (10, 3, 14, 7, 'Pass'),
      (6, 2, 15, 7, 'Fail'),
      (7, 1, 16, 7, 'Fail'),


      (5, 2, 1, 8, 'Fail'),  -- User 8 (quiz_id từ 1-21)
      (9, 1, 2, 8, 'Pass'),
      (8, 3, 3, 8, 'Pass'),
      (7, 2, 4, 8, 'Fail'),
      (10, 1, 5, 8, 'Pass'),
      (6, 3, 6, 8, 'Fail'),
      (9, 2, 7, 8, 'Pass'),
      (4, 1, 8, 8, 'Fail'),
      (10, 2, 9, 8, 'Pass'),
      (7, 1, 10, 8, 'Fail'),
      (8, 3, 11, 8, 'Pass'),
      (5, 2, 12, 8, 'Fail'),
      (9, 1, 13, 8, 'Pass'),
      (10, 3, 14, 8, 'Pass'),
      (6, 2, 15, 8, 'Fail'),
      (7, 1, 16, 8, 'Fail'),
      (8, 2, 17, 8, 'Pass'),
      (10, 3, 18, 8, 'Pass'),
      (9, 1, 19, 8, 'Pass'),
      (5, 2, 20, 8, 'Fail'),
      (10, 3, 21, 8, 'Pass'),


      (8, 1, 1, 9, 'Pass'),  -- User 9 (quiz_id từ 1-3)
      (10, 2, 2, 9, 'Pass'),
      (6, 3, 3, 9, 'Fail'),


      (7, 2, 1, 10, 'Fail'), -- User 10 (quiz_id từ 1-13)
      (9, 1, 2, 10, 'Pass'),
      (8, 3, 3, 10, 'Pass'),
      (5, 2, 4, 10, 'Fail'),
      (10, 1, 5, 10, 'Pass'),
      (6, 3, 6, 10, 'Fail'),
      (9, 2, 7, 10, 'Pass'),
      (4, 1, 8, 10, 'Fail'),
      (10, 2, 9, 10, 'Pass'),
      (7, 1, 10, 10, 'Fail'),
      (8, 3, 11, 10, 'Pass'),
      (5, 2, 12, 10, 'Fail'),
      (9, 1, 13, 10, 'Pass'),


      (6, 2, 1, 11, 'Fail'), -- User 11 (quiz_id từ 1-33)
      (9, 1, 2, 11, 'Pass'),
      (7, 3, 3, 11, 'Fail'),
      (10, 2, 4, 11, 'Pass'),
      (8, 1, 5, 11, 'Pass'),
      (5, 3, 6, 11, 'Fail'),
      (9, 2, 7, 11, 'Pass'),
      (3, 1, 8, 11, 'Fail'),
      (10, 2, 9, 11, 'Pass'),
      (7, 1, 10, 11, 'Fail'),
      (8, 3, 11, 11, 'Pass'),
      (4, 2, 12, 11, 'Fail'),
      (9, 1, 13, 11, 'Pass'),
      (10, 3, 14, 11, 'Pass'),
      (6, 2, 15, 11, 'Fail'),
      (7, 1, 16, 11, 'Fail'),
      (8, 2, 17, 11, 'Pass'),
      (10, 3, 18, 11, 'Pass'),
      (9, 1, 19, 11, 'Pass'),
      (5, 2, 20, 11, 'Fail'),
      (10, 3, 21, 11, 'Pass'),
      (6, 1, 22, 11, 'Fail'),
      (7, 3, 23, 11, 'Fail'),
      (8, 2, 24, 11, 'Pass'),
      (9, 1, 25, 11, 'Pass'),
      (10, 3, 26, 11, 'Pass'),
      (5, 2, 27, 11, 'Fail'),
      (6, 1, 28, 11, 'Fail'),
      (8, 3, 29, 11, 'Pass'),
      (10, 2, 30, 11, 'Pass'),
      (9, 1, 31, 11, 'Pass'),
      (6, 2, 32, 11, 'Fail'),
      (7, 3, 33, 11, 'Fail'),


      (8, 1, 1, 12, 'Pass'), -- User 12 (quiz_id từ 1-35)
      (10, 2, 2, 12, 'Pass'),
      (6, 3, 3, 12, 'Fail'),
      (9, 2, 4, 12, 'Pass'),
      (8, 1, 5, 12, 'Pass'),
      (4, 3, 6, 12, 'Fail'),
      (9, 2, 7, 12, 'Pass'),
      (2, 1, 8, 12, 'Fail'),
      (10, 2, 9, 12, 'Pass'),
      (7, 1, 10, 12, 'Fail'),
      (8, 3, 11, 12, 'Pass'),
      (3, 2, 12, 12, 'Fail'),
      (9, 1, 13, 12, 'Pass'),
      (10, 3, 14, 12, 'Pass'),
      (6, 2, 15, 12, 'Fail'),
      (7, 1, 16, 12, 'Fail'),
      (8, 2, 17, 12, 'Pass'),
      (10, 3, 18, 12, 'Pass'),
      (9, 1, 19, 12, 'Pass'),
      (5, 2, 20, 12, 'Fail'),
      (10, 3, 21, 12, 'Pass'),
      (6, 1, 22, 12, 'Fail'),
      (7, 3, 23, 12, 'Fail'),
      (8, 2, 24, 12, 'Pass'),
      (9, 1, 25, 12, 'Pass'),
      (10, 3, 26, 12, 'Pass'),
      (5, 2, 27, 12, 'Fail'),
      (6, 1, 28, 12, 'Fail'),
      (8, 3, 29, 12, 'Pass'),
      (10, 2, 30, 12, 'Pass'),
      (9, 1, 31, 12, 'Pass'),
      (6, 2, 32, 12, 'Fail'),
      (7, 3, 33, 12, 'Fail'),
      (8, 2, 34, 12, 'Pass'),
      (10, 1, 35, 12, 'Pass'),


      (6, 2, 1, 13, 'Fail'), -- User 13 (quiz_id từ 1-9)
      (9, 1, 2, 13, 'Pass'),
      (7, 3, 3, 13, 'Fail'),
      (10, 2, 4, 13, 'Pass'),
      (8, 1, 5, 13, 'Pass'),
      (5, 3, 6, 13, 'Fail'),
      (9, 2, 7, 13, 'Pass'),
      (3, 1, 8, 13, 'Fail'),
      (10, 2, 9, 13, 'Pass'),


      (7, 2, 1, 14, 'Fail'), -- User 14 (quiz_id từ 1-6)
      (10, 1, 2, 14, 'Pass'),
      (6, 3, 3, 14, 'Fail'),
      (9, 2, 4, 14, 'Pass'),
      (8, 1, 5, 14, 'Pass'),
      (4, 3, 6, 14, 'Fail'),


      (5, 2, 1, 21, 'Fail'), -- User 21 (quiz_id từ 1-29)
      (9, 1, 2, 21, 'Pass'),
      (8, 3, 3, 21, 'Pass'),
      (7, 2, 4, 21, 'Fail'),
      (10, 1, 5, 21, 'Pass'),
      (6, 3, 6, 21, 'Fail'),
      (9, 2, 7, 21, 'Pass'),
      (4, 1, 8, 21, 'Fail'),
      (10, 2, 9, 21, 'Pass'),
      (7, 1, 10, 21, 'Fail'),
      (8, 3, 11, 21, 'Pass'),
      (5, 2, 12, 21, 'Fail'),
      (9, 1, 13, 21, 'Pass'),
      (10, 3, 14, 21, 'Pass'),
      (6, 2, 15, 21, 'Fail'),
      (7, 1, 16, 21, 'Fail'),
      (8, 2, 17, 21, 'Pass'),
      (10, 3, 18, 21, 'Pass'),
      (9, 1, 19, 21, 'Pass'),
      (5, 2, 20, 21, 'Fail'),
      (10, 3, 21, 21, 'Pass'),
      (6, 1, 22, 21, 'Fail'),
      (7, 3, 23, 21, 'Fail'),
      (8, 2, 24, 21, 'Pass'),
      (9, 1, 25, 21, 'Pass'),
      (10, 3, 26, 21, 'Pass'),
      (5, 2, 27, 21, 'Fail'),
      (6, 1, 28, 21, 'Fail'),
      (8, 3, 29, 21, 'Pass');



