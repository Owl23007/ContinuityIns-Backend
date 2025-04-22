use continuityins;

-- 一级分类（10个主类别）
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('技术', 0, 1, '计算机科学与软件开发相关话题');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('生活', 0, 2, '日常生活与实用技巧分享');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('教育', 0, 3, '学习资源与教学方法交流');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('健康', 0, 4, '身心健康与医疗保健知识');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('财经', 0, 5, '金融投资与经济趋势分析');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('娱乐', 0, 6, '影视音乐与休闲娱乐资讯');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('旅游', 0, 7, '旅行攻略与地理文化探索');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('体育', 0, 8, '体育运动与赛事报道');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('美食', 0, 9, '烹饪艺术与饮食文化');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('艺术', 0, 10, '创意设计与美学欣赏');

-- 技术类二级分类（10个）
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('编程语言', 1, 1, '各种编程语言特性与比较');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('前端开发', 1, 2, 'Web前端技术栈实践');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('后端架构', 1, 3, '服务器端开发与系统设计');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('数据库', 1, 4, '数据存储与管理方案');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('人工智能', 1, 5, '机器学习与深度学习应用');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('网络安全', 1, 6, '信息安全防护技术指南');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('云计算', 1, 7, '云服务平台使用实践');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('物联网', 1, 8, '智能硬件与嵌入式开发');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('开发工具', 1, 9, '效率工具与IDE使用技巧');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('开源项目', 1, 10, '优秀开源代码解析');

-- 技术类三级分类（每个二级分类2个）
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('Python实战', 11, 1, 'Python语言进阶与应用开发');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('Java体系', 11, 2, 'Java生态与企业级开发');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('React框架', 12, 1, 'React及其生态系统的深度使用');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('Vue生态', 12, 2, 'Vue.js框架最佳实践');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('微服务架构', 13, 1, '分布式系统设计与实现');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('RESTful API', 13, 2, '接口设计与开发规范');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('MySQL优化', 14, 1, '关系型数据库性能调优');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('NoSQL选型', 14, 2, '非关系型数据库应用场景');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('计算机视觉', 15, 1, '图像识别与处理技术');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('自然语言处理', 15, 2, 'NLP技术与应用实践');
-- 继续生成技术类三级分类...

-- 生活类二级分类（10个）
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('家居装修', 2, 1, '室内设计与空间优化');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('健康养生', 2, 2, '日常保健与中医调理');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('亲子教育', 2, 3, '儿童成长与家庭教育');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('情感关系', 2, 4, '人际交往与亲密关系维护');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('宠物饲养', 2, 5, '萌宠护理与训练指南');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('时间管理', 2, 6, '工作效率提升方法论');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('理财规划', 2, 7, '个人财富管理技巧');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('服饰搭配', 2, 8, '服装美学与个人形象设计');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('垃圾分类', 2, 9, '环保知识与实践指南');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('应急技能', 2, 10, '日常生活安全常识');

-- 生活类三级分类（每个二级分类2个）
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('北欧风格', 21, 1, '简约家居设计理念');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('智能家居', 21, 2, '物联网家电使用指南');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('中医推拿', 22, 1, '传统按摩保健手法');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('膳食营养', 22, 2, '科学饮食搭配方案');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('早教方法', 23, 1, '0-3岁幼儿启蒙教育');
INSERT INTO categories (name, parent_id, sort_order, description) VALUES ('青春期教育', 23, 2, '青少年成长问题指导');
