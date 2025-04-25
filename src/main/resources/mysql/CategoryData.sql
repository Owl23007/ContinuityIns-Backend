use continuityins;

-- 一级分类（10个主类别）
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('科技', 0, 1, '涵盖编程、人工智能、软件开发等技术话题'),
                                                                      ('生活', 0, 2, '日常生活、旅行见闻、情感记录等'),
                                                                      ('文学', 0, 3, '原创小说、散文、诗歌等创作'),
                                                                      ('学习', 0, 4, '学习方法、知识整理、技能提升'),
                                                                      ('资源', 0, 5, '实用工具、书籍资料、网站推荐'),
                                                                      ('艺术', 0, 6, '影视音乐、绘画设计等艺术领域'),
                                                                      ('数码', 0, 7, '电子产品评测、APP使用技巧等'),
                                                                      ('游戏', 0, 8, '电子游戏与文化现象解析'),
                                                                      ('美食', 0, 9, '烹饪技法与饮食文化研究'),
                                                                      ('杂谈', 0, 10, '无法归类到其他类别的随想短文');

-- 科技子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('编程开发', 1, 1, '编程语言与开发技术实践'),
                                                                      ('人工智能', 1, 2, 'AI技术与机器学习应用'),
                                                                      ('前沿科技', 1, 3, '新兴技术趋势与行业动态');

-- 生活子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('旅行游记', 2, 1, '各地旅行见闻与攻略'),
                                                                      ('情感日记', 2, 2, '个人情感与生活感悟'),
                                                                      ('家居生活', 2, 3, '家装设计与生活技巧');

-- 文学子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('短篇创作', 3, 1, '微型小说与闪小说'),
                                                                      ('散文随笔', 3, 2, '叙事散文与抒情散文'),
                                                                      ('诗歌词曲', 3, 3, '现代诗与古典诗词');

-- 学习子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('语言学习', 4, 1, '外语学习方法与资源'),
                                                                      ('效率提升', 4, 2, '学习方法与知识管理'),
                                                                      ('学术笔记', 4, 3, '论文阅读与研究心得');

-- 资源子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('软件工具', 5, 1, '生产力工具推荐'),
                                                                      ('书单影单', 5, 2, '优质书籍影视推荐'),
                                                                      ('资源合集', 5, 3, '专题资源包与清单');

-- 艺术子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('影视评论', 6, 1, '电影电视剧深度解析'),
                                                                      ('音乐舞蹈', 6, 2, '音乐作品与表演艺术'),
                                                                      ('视觉艺术', 6, 3, '绘画摄影与设计作品');

-- 数码子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('硬件评测', 7, 1, '电子产品开箱与测评'),
                                                                      ('APP指南', 7, 2, '移动应用使用技巧'),
                                                                      ('智能设备', 7, 3, '智能家居与穿戴设备');

-- 游戏子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('游戏评测', 8, 1, '游戏作品体验报告'),
                                                                      ('电竞风云', 8, 2, '电竞赛事与战队动态'),
                                                                      ('游戏文化', 8, 3, '游戏史与玩家文化');

-- 美食子分类
INSERT INTO categories (name, parent_id, sort_order, description) VALUES
                                                                      ('食谱教程', 9, 1, '烹饪方法与菜谱分享'),
                                                                      ('探店食记', 9, 2, '餐厅体验与美食探索'),
                                                                      ('饮食文化', 9, 3, '各地饮食文化研究');