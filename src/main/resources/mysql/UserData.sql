USE continuityins;
-- 插入100条用户数据
INSERT INTO users (username, nickname, signature, avatar_image, background_image, email, encr_password, salt, status, create_time, update_time) VALUES
('john_doe', 'John Doe', 'Life is beautiful!', NULL, NULL, 'john.doe@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
('jane_smith', 'Jane Smith', 'Dream big!', NULL, NULL, 'jane.smith@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-02 11:15:00', '2023-01-02 11:15:00'),
('mike_johnson', 'Mike Johnson', 'Coding is life', NULL, NULL, 'mike.johnson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-03 09:30:00', '2023-01-03 09:30:00'),
('sarah_williams', 'Sarah Williams', 'Music lover', NULL, NULL, 'sarah.williams@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-04 14:45:00', '2023-01-04 14:45:00'),
('david_brown', 'David Brown', 'Travel enthusiast', NULL, NULL, 'david.brown@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-05 16:20:00', '2023-01-05 16:20:00'),
('lisa_jones', 'Lisa Jones', 'Bookworm', NULL, NULL, 'lisa.jones@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-06 13:10:00', '2023-01-06 13:10:00'),
('robert_miller', 'Robert Miller', 'Fitness freak', NULL, NULL, 'robert.miller@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-07 08:45:00', '2023-01-07 08:45:00'),
('emily_davis', 'Emily Davis', 'Art is my passion', NULL, NULL, 'emily.davis@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-08 12:30:00', '2023-01-08 12:30:00'),
('thomas_wilson', 'Thomas Wilson', 'Tech geek', NULL, NULL, 'thomas.wilson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-09 15:20:00', '2023-01-09 15:20:00'),
('jennifer_taylor', 'Jennifer Taylor', 'Foodie', NULL, NULL, 'jennifer.taylor@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-10 10:10:00', '2023-01-10 10:10:00'),
('william_anderson', 'William Anderson', 'Photography is my life', NULL, NULL, 'william.anderson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-11 09:15:00', '2023-01-11 09:15:00'),
('jessica_thomas', 'Jessica Thomas', 'Yoga practitioner', NULL, NULL, 'jessica.thomas@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-12 14:30:00', '2023-01-12 14:30:00'),
('charles_moore', 'Charles Moore', 'Coffee addict', NULL, NULL, 'charles.moore@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-13 16:45:00', '2023-01-13 16:45:00'),
('amanda_martin', 'Amanda Martin', 'Dog lover', NULL, NULL, 'amanda.martin@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-14 11:20:00', '2023-01-14 11:20:00'),
('daniel_jackson', 'Daniel Jackson', 'Gamer for life', NULL, NULL, 'daniel.jackson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-15 13:40:00', '2023-01-15 13:40:00'),
('michelle_white', 'Michelle White', 'Fashionista', NULL, NULL, 'michelle.white@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-16 10:50:00', '2023-01-16 10:50:00'),
('christopher_harris', 'Christopher Harris', 'Outdoor adventurer', NULL, NULL, 'christopher.harris@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-17 09:25:00', '2023-01-17 09:25:00'),
('ashley_clark', 'Ashley Clark', 'Movie buff', NULL, NULL, 'ashley.clark@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-18 15:15:00', '2023-01-18 15:15:00'),
('matthew_lewis', 'Matthew Lewis', 'Chess player', NULL, NULL, 'matthew.lewis@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-19 14:05:00', '2023-01-19 14:05:00'),
('stephanie_walker', 'Stephanie Walker', 'Nature lover', NULL, NULL, 'stephanie.walker@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-20 12:30:00', '2023-01-20 12:30:00'),
('kevin_hall', 'Kevin Hall', 'Car enthusiast', NULL, NULL, 'kevin.hall@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-21 11:45:00', '2023-01-21 11:45:00'),
('laura_young', 'Laura Young', 'Plant mom', NULL, NULL, 'laura.young@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-22 10:20:00', '2023-01-22 10:20:00'),
('ryan_king', 'Ryan King', 'Sports fanatic', NULL, NULL, 'ryan.king@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-23 09:10:00', '2023-01-23 09:10:00'),
('rebecca_green', 'Rebecca Green', 'DIY queen', NULL, NULL, 'rebecca.green@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-24 14:25:00', '2023-01-24 14:25:00'),
('jason_adams', 'Jason Adams', 'Beer connoisseur', NULL, NULL, 'jason.adams@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-25 16:40:00', '2023-01-25 16:40:00'),
('hannah_nelson', 'Hannah Nelson', 'Dance like nobody''s watching', NULL, NULL, 'hannah.nelson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-26 13:15:00', '2023-01-26 13:15:00'),
('nicholas_baker', 'Nicholas Baker', 'History buff', NULL, NULL, 'nicholas.baker@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-27 11:30:00', '2023-01-27 11:30:00'),
('samantha_carter', 'Samantha Carter', 'Science nerd', NULL, NULL, 'samantha.carter@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-28 10:45:00', '2023-01-28 10:45:00'),
('justin_mitchell', 'Justin Mitchell', 'Guitar player', NULL, NULL, 'justin.mitchell@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-29 09:20:00', '2023-01-29 09:20:00'),
('olivia_roberts', 'Olivia Roberts', 'Wanderlust', NULL, NULL, 'olivia.roberts@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-30 14:35:00', '2023-01-30 14:35:00'),
('brandon_cook', 'Brandon Cook', 'Fitness coach', NULL, NULL, 'brandon.cook@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-01-31 16:50:00', '2023-01-31 16:50:00'),
('natalie_morgan', 'Natalie Morgan', 'Makeup artist', NULL, NULL, 'natalie.morgan@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-01 13:25:00', '2023-02-01 13:25:00'),
('gary_bell', 'Gary Bell', 'Stand-up comedian', NULL, NULL, 'gary.bell@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-02 11:40:00', '2023-02-02 11:40:00'),
('victoria_murphy', 'Victoria Murphy', 'Book collector', NULL, NULL, 'victoria.murphy@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-03 10:55:00', '2023-02-03 10:55:00'),
('patrick_bailey', 'Patrick Bailey', 'Coffee roaster', NULL, NULL, 'patrick.bailey@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-04 09:30:00', '2023-02-04 09:30:00'),
('rachel_cooper', 'Rachel Cooper', 'Interior designer', NULL, NULL, 'rachel.cooper@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-05 14:45:00', '2023-02-05 14:45:00'),
('peter_richardson', 'Peter Richardson', 'Astronomy lover', NULL, NULL, 'peter.richardson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-06 16:00:00', '2023-02-06 16:00:00'),
('melissa_howard', 'Melissa Howard', 'Baking enthusiast', NULL, NULL, 'melissa.howard@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-07 13:35:00', '2023-02-07 13:35:00'),
('keith_ward', 'Keith Ward', 'Cyclist', NULL, NULL, 'keith.ward@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-08 11:50:00', '2023-02-08 11:50:00'),
('angela_torres', 'Angela Torres', 'Language learner', NULL, NULL, 'angela.torres@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-09 10:05:00', '2023-02-09 10:05:00'),
('scott_peterson', 'Scott Peterson', 'Fishing expert', NULL, NULL, 'scott.peterson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-10 09:40:00', '2023-02-10 09:40:00'),
('kimberly_gray', 'Kimberly Gray', 'Puzzle solver', NULL, NULL, 'kimberly.gray@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-11 14:55:00', '2023-02-11 14:55:00'),
('gregory_ramirez', 'Gregory Ramirez', 'Stock trader', NULL, NULL, 'gregory.ramirez@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-12 16:10:00', '2023-02-12 16:10:00'),
('tiffany_james', 'Tiffany James', 'Fashion designer', NULL, NULL, 'tiffany.james@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-13 13:45:00', '2023-02-13 13:45:00'),
('dennis_watson', 'Dennis Watson', 'Marathon runner', NULL, NULL, 'dennis.watson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-14 12:00:00', '2023-02-14 12:00:00'),
('brittany_brooks', 'Brittany Brooks', 'Animal rights activist', NULL, NULL, 'brittany.brooks@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-15 10:15:00', '2023-02-15 10:15:00'),
('jeremy_kelly', 'Jeremy Kelly', 'Tech reviewer', NULL, NULL, 'jeremy.kelly@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-16 09:50:00', '2023-02-16 09:50:00'),
('vanessa_sanders', 'Vanessa Sanders', 'Poetry writer', NULL, NULL, 'vanessa.sanders@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-17 15:05:00', '2023-02-17 15:05:00'),
('ronald_price', 'Ronald Price', 'Vintage car collector', NULL, NULL, 'ronald.price@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-18 16:20:00', '2023-02-18 16:20:00'),
('crystal_bennett', 'Crystal Bennett', 'Minimalist', NULL, NULL, 'crystal.bennett@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-19 13:55:00', '2023-02-19 13:55:00'),
('alexander_wood', 'Alexander Wood', 'Mountain climber', NULL, NULL, 'alexander.wood@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-20 12:10:00', '2023-02-20 12:10:00'),
('monica_barnes', 'Monica Barnes', 'Youtuber', NULL, NULL, 'monica.barnes@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-21 10:25:00', '2023-02-21 10:25:00'),
('travis_ross', 'Travis Ross', 'DJ', NULL, NULL, 'travis.ross@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-22 09:00:00', '2023-02-22 09:00:00'),
('kelly_henderson', 'Kelly Henderson', 'Gardener', NULL, NULL, 'kelly.henderson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-23 14:15:00', '2023-02-23 14:15:00'),
('marcus_coleman', 'Marcus Coleman', 'Chef', NULL, NULL, 'marcus.coleman@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-24 16:30:00', '2023-02-24 16:30:00'),
('allison_jenkins', 'Allison Jenkins', 'Photographer', NULL, NULL, 'allison.jenkins@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-25 13:05:00', '2023-02-25 13:05:00'),
('nathan_perry', 'Nathan Perry', 'Gamer', NULL, NULL, 'nathan.perry@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-26 11:20:00', '2023-02-26 11:20:00'),
('diana_powell', 'Diana Powell', 'Artist', NULL, NULL, 'diana.powell@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-27 10:35:00', '2023-02-27 10:35:00'),
('carlos_long', 'Carlos Long', 'Writer', NULL, NULL, 'carlos.long@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-02-28 09:10:00', '2023-02-28 09:10:00'),
('heather_patterson', 'Heather Patterson', 'Dancer', NULL, NULL, 'heather.patterson@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-01 14:25:00', '2023-03-01 14:25:00'),
('shawn_hughes', 'Shawn Hughes', 'Musician', NULL, NULL, 'shawn.hughes@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-02 16:40:00', '2023-03-02 16:40:00'),
('maria_flores', 'Maria Flores', 'Teacher', NULL, NULL, 'maria.flores@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-03 13:15:00', '2023-03-03 13:15:00'),
('phillip_washington', 'Phillip Washington', 'Engineer', NULL, NULL, 'phillip.washington@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-04 11:30:00', '2023-03-04 11:30:00'),
('tara_butler', 'Tara Butler', 'Nurse', NULL, NULL, 'tara.butler@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-05 10:45:00', '2023-03-05 10:45:00'),
('jonathan_simmons', 'Jonathan Simmons', 'Architect', NULL, NULL, 'jonathan.simmons@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-06 09:20:00', '2023-03-06 09:20:00'),
('christine_foster', 'Christine Foster', 'Doctor', NULL, NULL, 'christine.foster@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-07 14:35:00', '2023-03-07 14:35:00'),
('aaron_gonzales', 'Aaron Gonzales', 'Lawyer', NULL, NULL, 'aaron.gonzales@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-08 16:50:00', '2023-03-08 16:50:00'),
('veronica_bryant', 'Veronica Bryant', 'Scientist', NULL, NULL, 'veronica.bryant@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-09 13:25:00', '2023-03-09 13:25:00'),
('kyle_russell', 'Kyle Russell', 'Pilot', NULL, NULL, 'kyle.russell@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-10 11:40:00', '2023-03-10 11:40:00'),
('erin_griffin', 'Erin Griffin', 'Journalist', NULL, NULL, 'erin.griffin@example.com', 'tMgK9uolCJJtt7Ije7a7n4Wawozhsf3vg372V3eUaIY=', 'f8644d9439874b7087435c9ee5e7acc0', 'NORMAL', '2023-03-11 10:55:00', '2023-03-11 10:55:00');

INSERT INTO users
(username, nickname, signature, avatar_image, background_image, email, token, token_expiration, encr_password, salt, status, last_login, last_login_ip, create_time)
VALUES
-- 普通用户
('alice_wang', 'Alice', '热爱生活的设计师', 'https://example.com/avatar1.jpg', 'https://example.com/bg1.jpg', 'alice@example.com', 'abcd1234', '2025-05-01 12:00:00', 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'NORMAL', '2025-04-18 20:15:00', '192.168.1.100', '2024-03-01 08:30:00'),
-- 未验证用户
('bob_chen', 'Bob', '新注册用户', 'https://example.com/avatar2.jpg', NULL, 'bob@example.com', NULL, NULL, 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'UNVERIFIED', NULL, NULL, '2024-04-10 15:00:00'),
-- 被封禁用户
('carol_li', 'Carol', '违规用户', 'https://example.com/avatar3.jpg', 'https://example.com/bg3.jpg', 'carol@example.com', 'expired_token', '2024-12-31 23:59:59', 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'BANNED', '2024-11-05 17:30:00', '10.0.0.50', '2023-09-15 11:20:00'),
-- 带有特殊字符的用户名
('david.zhang_007', 'David-Z', '技术宅', 'https://example.com/avatar4.jpg', NULL, 'david.z@example.com', 'valid_token_123', '2025-06-30 00:00:00', 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'NORMAL', '2025-04-17 09:45:00', '24.48.0.1', '2024-01-20 14:10:00');

INSERT INTO users
(username, nickname, signature, avatar_image, background_image, email, token, token_expiration, encr_password, salt, status, last_login, last_login_ip, create_time)
VALUES
-- IPv6地址案例 [[2]][[4]]
('ethan_dev', '以太', '区块链开发者', 'https://example.com/avatar5.jpg', NULL, 'ethan@dev.com', 'ipv6_token', '2025-07-01 00:00:00', 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'NORMAL', '2025-04-19 08:00:00', '2001:db8::1', '2024-05-05 17:22:00'),
-- 使用Midjourney风格头像 [[7]]
('fiona_art', '菲奥娜', 'AI艺术探索者', 'https://example.com/avatar_mj6.jpg', 'https://example.com/bg_art.jpg', 'fiona.art@example.net', NULL, NULL, 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'DEACTIVATED', '2024-12-01 19:00:00', '170.187.142.219', '2023-11-11 11:11:00'),
-- Faker生成的中文地址 [[9]]
('grace_travel', '环球客', '走过32个国家的旅行家', 'https://example.com/avatar7.jpg', NULL, 'grace@travel.org', 'expired_token_2024', '2024-01-01 00:00:00', 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'BANNED', '2024-03-15 14:30:00', '2404:6800:4003:c02::65', '2023-08-25 09:00:00'),
-- 多语言签名案例 [[1]]
('hiroshi_jp', 'ヒロシ', '日本語/English bilingual', 'https://example.com/avatar8.png', 'https://example.com/bg_jp.jpg', 'hiroshi@example.jp', 'valid_jp_token', '2025-12-31 23:59:59', 'BPUOGKgdUZC8KvF+yzuTjy93HEJYRxOb4IQKrHIFVLE=', 'ca0043cb04c3453996701cf0af7efa56', 'NORMAL', '2025-04-18 23:45:00', '124.156.23.89', '2024-02-28 16:00:00');


SELECT * FROM articles WHERE user_id = 100001;