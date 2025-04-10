Use continuityins;
-- DRAFT(10%)：30条 PENDING(30%)：60条 PUBLISHED(20%)：150条 BANNED(10%)：30条 PRIVATE(10%)：30条
-- 步骤1：创建存储过程
DELIMITER $$
CREATE PROCEDURE GenerateArticleData()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE user_count INT DEFAULT 70;
    DECLARE status_val VARCHAR(20);

    -- 步骤2：精确控制比例
    WHILE i < 300 DO
            SET status_val = CASE
                                 WHEN i < 30 THEN 'DRAFT'
                                 WHEN i < 90 THEN 'PENDING'
                                 WHEN i < 240 THEN 'PUBLISHED'
                                 WHEN i < 270 THEN 'BANNED'
                                 ELSE 'PRIVATE'
                END;

            -- 步骤3：动态生成内容
            INSERT INTO articles (title, user_id, content, status)
            VALUES (
                       CONCAT('测试文章标题-', i+1),
                       100001 + FLOOR(RAND() * user_count),
                       CONCAT(
                               '本文是系统生成的示例内容，用于测试数据库性能和数据展示效果。',
                               '内容长度经过严格计算，确保符合50-500字的规范要求。',
                               '当前为第', i+1, '条测试数据，状态为：', status_val,
                               REPEAT('.', 200 + FLOOR(RAND() * 250))  -- 动态填充内容长度
                       ),
                       status_val
                   );

            SET i = i + 1;
        END WHILE;
END$$
DELIMITER ;

-- 步骤4：执行生成
CALL GenerateArticleData();