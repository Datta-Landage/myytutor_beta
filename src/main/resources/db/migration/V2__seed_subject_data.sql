-- ===========================================================================
-- V2__seed_subject_data.sql
-- MyyTutor - Subject and Class Reference Data
-- Created: 2026-01-01
-- 
-- Structure:
--   subject_class: Only class 1-12 with common subjects + "All Subjects (Board)" entries
--   extra_subject: All other subjects including higher education, professional skills
-- ===========================================================================

-- ===========================================================================
-- SUBJECT CLASS DATA (Classes 1-12 only)
-- Each class has common subjects + "All Subjects" entries for different boards
-- ===========================================================================

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE subject_class;
TRUNCATE TABLE extra_subject;
SET FOREIGN_KEY_CHECKS = 1;

-- Class 1
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(1, 'English'),
(1, 'Hindi'),
(1, 'Mathematics'),
(1, 'Environmental Studies (EVS)'),
(1, 'General Knowledge'),
(1, 'All Subjects (CBSE)'),
(1, 'All Subjects (State Board)'),
(1, 'All Subjects (ICSE)'),
(1, 'All Subjects (International School)');

-- Class 2
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(2, 'English'),
(2, 'Hindi'),
(2, 'Mathematics'),
(2, 'Environmental Studies (EVS)'),
(2, 'General Knowledge'),
(2, 'All Subjects (CBSE)'),
(2, 'All Subjects (State Board)'),
(2, 'All Subjects (ICSE)'),
(2, 'All Subjects (International School)');

-- Class 3
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(3, 'English'),
(3, 'Hindi'),
(3, 'Mathematics'),
(3, 'Environmental Studies (EVS)'),
(3, 'General Knowledge'),
(3, 'Computer Basics'),
(3, 'All Subjects (CBSE)'),
(3, 'All Subjects (State Board)'),
(3, 'All Subjects (ICSE)'),
(3, 'All Subjects (International School)');

-- Class 4
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(4, 'English'),
(4, 'Hindi'),
(4, 'Mathematics'),
(4, 'Environmental Studies (EVS)'),
(4, 'General Knowledge'),
(4, 'Computer Basics'),
(4, 'All Subjects (CBSE)'),
(4, 'All Subjects (State Board)'),
(4, 'All Subjects (ICSE)'),
(4, 'All Subjects (International School)');

-- Class 5
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(5, 'English'),
(5, 'Hindi'),
(5, 'Marathi'),
(5, 'Mathematics'),
(5, 'Science'),
(5, 'Social Studies'),
(5, 'Computer Science'),
(5, 'General Knowledge'),
(5, 'All Subjects (CBSE)'),
(5, 'All Subjects (State Board)'),
(5, 'All Subjects (ICSE)'),
(5, 'All Subjects (International School)');

-- Class 6
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(6, 'English'),
(6, 'Hindi'),
(6, 'Marathi'),
(6, 'Sanskrit'),
(6, 'Mathematics'),
(6, 'Science'),
(6, 'Social Studies'),
(6, 'History'),
(6, 'Geography'),
(6, 'Computer Science'),
(6, 'General Knowledge'),
(6, 'All Subjects (CBSE)'),
(6, 'All Subjects (State Board)'),
(6, 'All Subjects (ICSE)'),
(6, 'All Subjects (International School)');

-- Class 7
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(7, 'English'),
(7, 'Hindi'),
(7, 'Marathi'),
(7, 'Sanskrit'),
(7, 'Mathematics'),
(7, 'Science'),
(7, 'Social Studies'),
(7, 'History'),
(7, 'Geography'),
(7, 'Computer Science'),
(7, 'General Knowledge'),
(7, 'All Subjects (CBSE)'),
(7, 'All Subjects (State Board)'),
(7, 'All Subjects (ICSE)'),
(7, 'All Subjects (International School)');

-- Class 8
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(8, 'English'),
(8, 'Hindi'),
(8, 'Marathi'),
(8, 'Sanskrit'),
(8, 'Mathematics'),
(8, 'Science'),
(8, 'Social Studies'),
(8, 'History'),
(8, 'Geography'),
(8, 'Computer Science'),
(8, 'General Knowledge'),
(8, 'All Subjects (CBSE)'),
(8, 'All Subjects (State Board)'),
(8, 'All Subjects (ICSE)'),
(8, 'All Subjects (International School)');

-- Class 9
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(9, 'English'),
(9, 'Hindi'),
(9, 'Marathi'),
(9, 'Sanskrit'),
(9, 'Mathematics'),
(9, 'Science'),
(9, 'Social Science'),
(9, 'History'),
(9, 'Geography'),
(9, 'Civics'),
(9, 'Computer Applications'),
(9, 'Information Technology'),
(9, 'All Subjects (CBSE)'),
(9, 'All Subjects (Maharashtra SSC)'),
(9, 'All Subjects (ICSE)'),
(9, 'All Subjects (IGCSE)'),
(9, 'All Subjects (International School)');

-- Class 10
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(10, 'English'),
(10, 'Hindi'),
(10, 'Marathi'),
(10, 'Sanskrit'),
(10, 'Mathematics'),
(10, 'Science'),
(10, 'Social Science'),
(10, 'History'),
(10, 'Geography'),
(10, 'Civics'),
(10, 'Computer Applications'),
(10, 'Information Technology'),
(10, 'All Subjects (CBSE)'),
(10, 'All Subjects (Maharashtra SSC)'),
(10, 'All Subjects (ICSE)'),
(10, 'All Subjects (IGCSE)'),
(10, 'All Subjects (International School)');

-- Class 11
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(11, 'English'),
(11, 'Physics'),
(11, 'Chemistry'),
(11, 'Mathematics'),
(11, 'Biology'),
(11, 'Computer Science'),
(11, 'Economics'),
(11, 'Accountancy'),
(11, 'Business Studies'),
(11, 'History'),
(11, 'Geography'),
(11, 'Political Science'),
(11, 'Psychology'),
(11, 'Sociology'),
(11, 'All Subjects - Science (CBSE)'),
(11, 'All Subjects - Commerce (CBSE)'),
(11, 'All Subjects - Arts (CBSE)'),
(11, 'All Subjects - Science (Maharashtra HSC)'),
(11, 'All Subjects - Commerce (Maharashtra HSC)'),
(11, 'All Subjects - Arts (Maharashtra HSC)'),
(11, 'All Subjects - Science (ISC)'),
(11, 'All Subjects - Commerce (ISC)'),
(11, 'All Subjects (IB Diploma)'),
(11, 'All Subjects (Cambridge AS Level)');

-- Class 12
INSERT IGNORE INTO subject_class (class_id, subject_name) VALUES
(12, 'English'),
(12, 'Physics'),
(12, 'Chemistry'),
(12, 'Mathematics'),
(12, 'Biology'),
(12, 'Computer Science'),
(12, 'Economics'),
(12, 'Accountancy'),
(12, 'Business Studies'),
(12, 'History'),
(12, 'Geography'),
(12, 'Political Science'),
(12, 'Psychology'),
(12, 'Sociology'),
(12, 'All Subjects - Science (CBSE)'),
(12, 'All Subjects - Commerce (CBSE)'),
(12, 'All Subjects - Arts (CBSE)'),
(12, 'All Subjects - Science (Maharashtra HSC)'),
(12, 'All Subjects - Commerce (Maharashtra HSC)'),
(12, 'All Subjects - Arts (Maharashtra HSC)'),
(12, 'All Subjects - Science (ISC)'),
(12, 'All Subjects - Commerce (ISC)'),
(12, 'All Subjects (IB Diploma)'),
(12, 'All Subjects (Cambridge A Level)');

-- ===========================================================================
-- EXTRA SUBJECTS (IDs starting from 1000)
-- All higher education, professional skills, and additional subjects
-- ===========================================================================

ALTER TABLE extra_subject AUTO_INCREMENT = 1000;

-- ===========================================
-- DIPLOMA ENGINEERING SUBJECTS (1000-1049)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
-- Diploma First Year
('Diploma - Engineering Mathematics I'),
('Diploma - Engineering Mathematics II'),
('Diploma - Engineering Physics'),
('Diploma - Engineering Chemistry'),
('Diploma - Basic Electrical Engineering'),
('Diploma - Basic Electronics'),
('Diploma - Engineering Drawing'),
('Diploma - Workshop Practice'),
('Diploma - All Subjects (1st Year)'),

-- Diploma Computer Engineering
('Diploma - Data Structures (Computer)'),
('Diploma - OOP with C++ (Computer)'),
('Diploma - DBMS (Computer)'),
('Diploma - Computer Networks (Computer)'),
('Diploma - Operating System (Computer)'),
('Diploma - Web Technologies (Computer)'),
('Diploma - Java Programming (Computer)'),
('Diploma - Python Programming (Computer)'),
('Diploma - All Subjects (Computer 2nd Year)'),
('Diploma - All Subjects (Computer 3rd Year)'),

-- Diploma Mechanical Engineering
('Diploma - Engineering Mechanics (Mechanical)'),
('Diploma - Thermodynamics (Mechanical)'),
('Diploma - Manufacturing Process (Mechanical)'),
('Diploma - Machine Design (Mechanical)'),
('Diploma - All Subjects (Mechanical 2nd Year)'),
('Diploma - All Subjects (Mechanical 3rd Year)'),

-- Diploma Electrical Engineering
('Diploma - Electrical Circuits (Electrical)'),
('Diploma - Electrical Machines (Electrical)'),
('Diploma - Power Systems (Electrical)'),
('Diploma - Control Systems (Electrical)'),
('Diploma - All Subjects (Electrical 2nd Year)'),
('Diploma - All Subjects (Electrical 3rd Year)'),

-- Diploma Civil Engineering
('Diploma - Surveying (Civil)'),
('Diploma - Building Construction (Civil)'),
('Diploma - Structural Mechanics (Civil)'),
('Diploma - All Subjects (Civil 2nd Year)'),
('Diploma - All Subjects (Civil 3rd Year)'),

-- Diploma Electronics Engineering
('Diploma - Digital Electronics'),
('Diploma - Microprocessor'),
('Diploma - Embedded Systems'),
('Diploma - All Subjects (Electronics 2nd Year)'),
('Diploma - All Subjects (Electronics 3rd Year)');

-- ===========================================
-- B.TECH / DEGREE ENGINEERING (1050-1099)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
-- B.Tech First Year Common
('B.Tech - Engineering Mathematics I'),
('B.Tech - Engineering Mathematics II'),
('B.Tech - Engineering Physics'),
('B.Tech - Engineering Chemistry'),
('B.Tech - Programming in C'),
('B.Tech - Engineering Mechanics'),
('B.Tech - All Subjects (1st Year)'),

-- B.Tech Computer Science / IT
('B.Tech - Data Structures & Algorithms (CSE)'),
('B.Tech - Object Oriented Programming (CSE)'),
('B.Tech - Database Management System (CSE)'),
('B.Tech - Operating Systems (CSE)'),
('B.Tech - Computer Networks (CSE)'),
('B.Tech - Software Engineering (CSE)'),
('B.Tech - Compiler Design (CSE)'),
('B.Tech - Machine Learning (CSE)'),
('B.Tech - Artificial Intelligence (CSE)'),
('B.Tech - All Subjects (CSE 2nd Year)'),
('B.Tech - All Subjects (CSE 3rd Year)'),
('B.Tech - All Subjects (CSE 4th Year)'),
('B.Tech - All Subjects (IT 2nd Year)'),
('B.Tech - All Subjects (IT 3rd Year)'),
('B.Tech - All Subjects (IT 4th Year)'),

-- B.Tech Mechanical
('B.Tech - Thermodynamics (Mechanical)'),
('B.Tech - Fluid Mechanics (Mechanical)'),
('B.Tech - Machine Design (Mechanical)'),
('B.Tech - Manufacturing Technology (Mechanical)'),
('B.Tech - All Subjects (Mechanical 2nd Year)'),
('B.Tech - All Subjects (Mechanical 3rd Year)'),
('B.Tech - All Subjects (Mechanical 4th Year)'),

-- B.Tech Electrical
('B.Tech - Circuit Theory (Electrical)'),
('B.Tech - Electrical Machines (Electrical)'),
('B.Tech - Power Electronics (Electrical)'),
('B.Tech - All Subjects (Electrical 2nd Year)'),
('B.Tech - All Subjects (Electrical 3rd Year)'),
('B.Tech - All Subjects (Electrical 4th Year)'),

-- B.Tech Electronics & Communication
('B.Tech - Analog Electronics (ECE)'),
('B.Tech - Digital Electronics (ECE)'),
('B.Tech - Signals & Systems (ECE)'),
('B.Tech - Communication Systems (ECE)'),
('B.Tech - All Subjects (ECE 2nd Year)'),
('B.Tech - All Subjects (ECE 3rd Year)'),
('B.Tech - All Subjects (ECE 4th Year)'),

-- B.Tech Civil
('B.Tech - Structural Analysis (Civil)'),
('B.Tech - Geotechnical Engineering (Civil)'),
('B.Tech - Concrete Technology (Civil)'),
('B.Tech - All Subjects (Civil 2nd Year)'),
('B.Tech - All Subjects (Civil 3rd Year)'),
('B.Tech - All Subjects (Civil 4th Year)');

-- ===========================================
-- DEGREE / GRADUATION COURSES (1100-1149)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
-- B.Sc
('B.Sc - Physics'),
('B.Sc - Chemistry'),
('B.Sc - Mathematics'),
('B.Sc - Computer Science'),
('B.Sc - Electronics'),
('B.Sc - Biotechnology'),
('B.Sc - Microbiology'),
('B.Sc - All Subjects (B.Sc)'),

-- B.Com
('B.Com - Financial Accounting'),
('B.Com - Cost Accounting'),
('B.Com - Business Law'),
('B.Com - Taxation'),
('B.Com - Auditing'),
('B.Com - All Subjects (B.Com)'),

-- BBA / BCA
('BBA - Principles of Management'),
('BBA - Marketing Management'),
('BBA - Human Resource Management'),
('BBA - All Subjects (BBA)'),
('BCA - Programming Fundamentals'),
('BCA - Web Development'),
('BCA - All Subjects (BCA)'),

-- BA
('BA - English Literature'),
('BA - Hindi Literature'),
('BA - History'),
('BA - Political Science'),
('BA - Economics'),
('BA - Psychology'),
('BA - Sociology'),
('BA - All Subjects (BA)');

-- ===========================================
-- POST GRADUATION / MASTERS (1150-1179)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('M.Tech - All Subjects'),
('M.Sc - Physics'),
('M.Sc - Chemistry'),
('M.Sc - Mathematics'),
('M.Sc - Computer Science'),
('M.Com - All Subjects'),
('MBA - Marketing'),
('MBA - Finance'),
('MBA - HR'),
('MBA - Operations'),
('MBA - All Subjects'),
('MCA - All Subjects'),
('MA - English'),
('MA - Economics'),
('MA - Psychology');

-- ===========================================
-- PROGRAMMING & TECHNOLOGY (1180-1229)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Scratch Programming'),
('Block-based Coding'),
('Python for Kids'),
('Python Programming'),
('Python Advanced'),
('Java Core'),
('Java Advanced (Spring Boot)'),
('JavaScript Basics'),
('JavaScript Advanced (Node.js)'),
('TypeScript'),
('React.js Development'),
('Angular Development'),
('Vue.js Development'),
('C Programming'),
('C++ Programming'),
('C# Programming'),
('.NET Development'),
('ASP.NET Development'),
('PHP Development'),
('Laravel Development'),
('Ruby on Rails'),
('Go Programming'),
('Rust Programming'),
('Kotlin Programming'),
('Swift Programming'),
('Data Structures & Algorithms'),
('Competitive Programming'),
('Android Development (Java)'),
('Android Development (Kotlin)'),
('iOS Development (Swift)'),
('Flutter Development'),
('React Native Development'),
('Game Development (Unity)'),
('Game Development (Unreal)'),
('Robotics & Arduino'),
('Raspberry Pi'),
('Embedded Systems'),
('IoT Development');

-- ===========================================
-- SAP & ERP SYSTEMS (1230-1259)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('SAP ABAP Programming'),
('SAP FICO (Finance & Controlling)'),
('SAP MM (Materials Management)'),
('SAP SD (Sales & Distribution)'),
('SAP HR/HCM'),
('SAP BASIS Administration'),
('SAP HANA'),
('SAP S/4HANA'),
('SAP BW/BI'),
('SAP Fiori'),
('Oracle ERP'),
('Oracle SQL & PL/SQL'),
('Oracle DBA'),
('Oracle APEX'),
('Salesforce Development'),
('Salesforce Administration'),
('ServiceNow Development'),
('Microsoft Dynamics 365'),
('Tally ERP'),
('Tally Prime'),
('QuickBooks'),
('Zoho CRM');

-- ===========================================
-- DATA SCIENCE & AI (1260-1289)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Machine Learning Basics'),
('Machine Learning Advanced'),
('Deep Learning'),
('Natural Language Processing (NLP)'),
('Computer Vision'),
('Artificial Intelligence'),
('Data Science with Python'),
('Data Science with R'),
('Data Analytics'),
('Business Analytics'),
('Power BI'),
('Tableau'),
('Excel Advanced & VBA'),
('Statistics for Data Science'),
('Big Data (Hadoop)'),
('Apache Spark'),
('Data Engineering'),
('TensorFlow'),
('PyTorch'),
('Keras');

-- ===========================================
-- CLOUD & DEVOPS (1290-1319)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('AWS Cloud Fundamentals'),
('AWS Solutions Architect'),
('AWS Developer'),
('AWS DevOps'),
('Azure Fundamentals'),
('Azure Administrator'),
('Azure Developer'),
('Google Cloud Platform (GCP)'),
('Docker'),
('Kubernetes'),
('Jenkins'),
('Ansible'),
('Terraform'),
('CI/CD Pipeline'),
('Linux Administration'),
('Shell Scripting'),
('Git & GitHub'),
('DevOps Engineering');

-- ===========================================
-- DATABASE & BACKEND (1320-1349)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('MySQL'),
('PostgreSQL'),
('MongoDB'),
('Redis'),
('Cassandra'),
('SQL Server'),
('Database Design'),
('REST API Development'),
('GraphQL'),
('Microservices Architecture'),
('System Design'),
('Software Architecture');

-- ===========================================
-- CYBER SECURITY (1350-1379)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Cyber Security Basics'),
('Ethical Hacking'),
('Penetration Testing'),
('Network Security'),
('Web Application Security'),
('VAPT (Vulnerability Assessment)'),
('SOC Analyst'),
('Incident Response'),
('Malware Analysis'),
('Digital Forensics'),
('CISSP Preparation'),
('CEH Preparation'),
('CompTIA Security+');

-- ===========================================
-- COMPETITIVE EXAMS (1380-1419)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('JEE Main Preparation'),
('JEE Advanced Preparation'),
('NEET Preparation'),
('NEET PG Preparation'),
('Olympiad - Mathematics'),
('Olympiad - Science'),
('Olympiad - Cyber'),
('NTSE Preparation'),
('KVPY Preparation'),
('SAT Preparation'),
('GRE Preparation'),
('GMAT Preparation'),
('IELTS Preparation'),
('TOEFL Preparation'),
('CAT Preparation'),
('UPSC Foundation'),
('MPSC Preparation'),
('Bank Exams Preparation'),
('SSC Exams Preparation'),
('GATE - CSE'),
('GATE - ECE'),
('GATE - Mechanical'),
('GATE - Electrical'),
('GATE - Civil');

-- ===========================================
-- LANGUAGES (1420-1449)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Spoken English'),
('English Grammar'),
('English Creative Writing'),
('Business English'),
('Public Speaking & Debate'),
('French Language'),
('German Language'),
('Spanish Language'),
('Japanese Language'),
('Mandarin Chinese'),
('Korean Language'),
('Sanskrit Advanced');

-- ===========================================
-- ARTS & CREATIVITY (1450-1489)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Classical Vocal Music'),
('Western Vocal Music'),
('Hindustani Classical'),
('Carnatic Music'),
('Keyboard/Piano'),
('Guitar'),
('Violin'),
('Tabla'),
('Harmonium'),
('Bharatanatyam'),
('Kathak'),
('Contemporary Dance'),
('Hip Hop Dance'),
('Drawing & Sketching'),
('Painting'),
('Digital Art'),
('Graphic Design'),
('UI/UX Design'),
('Photography'),
('Video Editing'),
('Animation'),
('3D Modeling');

-- ===========================================
-- SPORTS & FITNESS (1490-1509)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Yoga & Meditation'),
('Physical Fitness'),
('Chess'),
('Badminton'),
('Cricket Coaching'),
('Football Coaching'),
('Swimming'),
('Karate'),
('Taekwondo');

-- ===========================================
-- LIFE SKILLS & PERSONAL DEVELOPMENT (1510-1539)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Vedic Mathematics'),
('Abacus & Mental Math'),
('Memory Techniques'),
('Speed Reading'),
('Personality Development'),
('Leadership Skills'),
('Career Counseling'),
('Communication Skills'),
('Interview Preparation'),
('Resume Building'),
('Financial Literacy'),
('Stock Market Basics'),
('Entrepreneurship');

-- ===========================================
-- PROFESSIONAL CERTIFICATIONS (1540-1569)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('PMP Certification'),
('Scrum Master'),
('Agile Methodology'),
('ITIL Foundation'),
('Six Sigma'),
('Digital Marketing'),
('SEO & SEM'),
('Google Analytics'),
('Social Media Marketing'),
('Content Writing'),
('Technical Writing'),
('MS Office Advanced'),
('AutoCAD'),
('SolidWorks'),
('MATLAB');

-- ===========================================================================
-- END OF SEED DATA
-- ===========================================================================
