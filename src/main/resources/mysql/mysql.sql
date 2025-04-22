-- 创建数据库
CREATE DATABASE IF NOT EXISTS ContinuityIns
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE ContinuityIns;

-- 用户表
CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
                       username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                       nickname VARCHAR(50) NOT NULL COMMENT '昵称',
                       signature VARCHAR(512) COMMENT '个性签名',
                       avatar_image VARCHAR(512) COMMENT '头像URL',
                       background_image VARCHAR(512) COMMENT '背景图URL',
                       email VARCHAR(255) NOT NULL UNIQUE COMMENT '邮箱',
                       encr_password CHAR(64) NOT NULL COMMENT '加密密码',
                       salt CHAR(32) NOT NULL COMMENT '密码盐值',
                       token VARCHAR(255) COMMENT '验证token',
                       token_expiration TIMESTAMP COMMENT 'token过期时间',
                       status ENUM('UNVERIFIED', 'NORMAL', 'BANNED', 'DEACTIVATED') NOT NULL DEFAULT 'UNVERIFIED' COMMENT '状态',
                       last_login TIMESTAMP NULL COMMENT '最后登录时间',
                       last_login_ip VARCHAR(45) COMMENT '最后登录IP',
                       create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       INDEX idx_user_status (status),
                       INDEX idx_user_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=100001;

-- 用户设置表
CREATE TABLE user_settings (
                               setting_id INT AUTO_INCREMENT PRIMARY KEY,
                               user_id INT NOT NULL UNIQUE,
                               theme ENUM('LIGHT', 'DARK', 'SYSTEM') DEFAULT 'SYSTEM',
                               notification_preferences JSON COMMENT '通知偏好设置',
                               privacy_settings JSON COMMENT '隐私设置',
                               create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 管理员表
CREATE TABLE admins (
                        admin_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员ID',
                        user_id INT NOT NULL UNIQUE COMMENT '关联用户ID',
                        role ENUM('SUPER_ADMIN', 'CONTENT_ADMIN', 'USER_ADMIN') NOT NULL DEFAULT 'CONTENT_ADMIN' COMMENT '角色类型',
                        permissions JSON COMMENT '权限配置（JSON格式）',
                        department VARCHAR(50) COMMENT '所属部门',
                        last_login_time TIMESTAMP NULL COMMENT '最后登录时间',
                        last_login_ip VARCHAR(45) COMMENT '最后登录IP',
                        create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章表
CREATE TABLE articles (
                          article_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID',
                          user_id INT NOT NULL COMMENT '作者ID',
                          title VARCHAR(150) NOT NULL COMMENT '标题',
                          content LONGTEXT NOT NULL COMMENT '内容',
                          summary VARCHAR(300) COMMENT '摘要',
                          cover_image VARCHAR(512) COMMENT '封面图URL',
                          status ENUM('DRAFT', 'PENDING', 'PUBLISHED', 'BANNED', 'PRIVATE') NOT NULL DEFAULT 'DRAFT' COMMENT '状态',
                          word_count SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '字数',
                          view_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览数',
                          like_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数',
                          comment_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论数',
                          collection_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '收藏数',
                          is_top TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
                          create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                          INDEX idx_article_status (status),
                          INDEX idx_article_time (create_time),
                          FULLTEXT INDEX idx_fulltext (title, content) WITH PARSER ngram
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章历史版本表（新增）
CREATE TABLE article_versions (
                                  version_id INT AUTO_INCREMENT PRIMARY KEY,
                                  article_id INT NOT NULL,
                                  user_id INT NOT NULL,
                                  title VARCHAR(150) NOT NULL,
                                  content LONGTEXT NOT NULL,
                                  word_count SMALLINT UNSIGNED NOT NULL,
                                  version_number INT NOT NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE,
                                  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                                  INDEX idx_article_version (article_id, version_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 分类表
CREATE TABLE categories (
                            category_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
                            name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称',
                            parent_id INT DEFAULT 0 COMMENT '父分类ID',
                            sort_order INT DEFAULT 0 COMMENT '排序序号',
                            description VARCHAR(200) COMMENT '分类描述',
                            create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            INDEX idx_category_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章分类关系表
CREATE TABLE article_categories (
                                    article_id INT NOT NULL COMMENT '文章ID',
                                    category_id INT NOT NULL COMMENT '分类ID',
                                    PRIMARY KEY (article_id, category_id),
                                    FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE,
                                    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 标签表
CREATE TABLE tags (
                      tag_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '标签ID',
                      tag_name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
                      user_id INT NOT NULL COMMENT '创建者ID',
                      is_official TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否官方标签',
                      description VARCHAR(200) COMMENT '标签描述',
                      status ENUM('ACTIVE', 'BANNED') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
                      create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      INDEX idx_tag_status (status),
                      FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章标签关系表
CREATE TABLE tag_articles (
                              tag_id INT NOT NULL COMMENT '标签ID',
                              article_id INT NOT NULL COMMENT '文章ID',
                              create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '关联时间',
                              PRIMARY KEY (tag_id, article_id),
                              FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE,
                              FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论表
CREATE TABLE comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '评论ID',
                          user_id INT NOT NULL COMMENT '评论者ID',
                          content VARCHAR(1000) NOT NULL COMMENT '评论内容',
                          target_id INT NOT NULL COMMENT '目标ID',
                          target_type ENUM('ARTICLE', 'COMMENT') NOT NULL COMMENT '目标类型',
                          parent_id INT DEFAULT NULL COMMENT '父评论ID（新增）',
                          status ENUM('PENDING', 'PUBLISHED', 'BANNED', 'DELETED') NOT NULL DEFAULT 'PENDING' COMMENT '状态',
                          reply_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '回复数',
                          create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                          FOREIGN KEY (parent_id) REFERENCES comments(comment_id) ON DELETE SET NULL,
                          INDEX idx_comment_target (target_type, target_id),
                          INDEX idx_comment_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 点赞系统
CREATE TABLE article_likes (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               article_id INT NOT NULL,
                               user_id INT NOT NULL,
                               create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UNIQUE KEY unique_article_like (article_id, user_id),
                               FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论点赞系统
CREATE TABLE comment_likes (
                               like_id INT AUTO_INCREMENT PRIMARY KEY,
                               comment_id INT NOT NULL,
                               user_id INT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UNIQUE KEY unique_like (comment_id, user_id),
                               FOREIGN KEY (comment_id) REFERENCES comments(comment_id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 收藏系统
CREATE TABLE article_collections (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     article_id INT NOT NULL,
                                     user_id INT NOT NULL,
                                     create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     UNIQUE KEY unique_article_collection (article_id, user_id),
                                     FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE,
                                     FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 浏览记录表
CREATE TABLE article_view_records (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      article_id INT NOT NULL,
                                      user_id INT NOT NULL,
                                      device_type VARCHAR(50) COMMENT '设备类型',
                                      ip_address VARCHAR(45) COMMENT 'IP地址',
                                      view_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE,
                                      FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                                      INDEX idx_view_time (view_time),
                                      INDEX idx_user_article (user_id, article_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 关注系统
CREATE TABLE user_follows (
                              follower_id INT NOT NULL,
                              following_id INT NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (follower_id, following_id),
                              FOREIGN KEY (follower_id) REFERENCES users(user_id) ON DELETE CASCADE,
                              FOREIGN KEY (following_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 私信系统
CREATE TABLE private_messages (
                                  message_id INT AUTO_INCREMENT PRIMARY KEY,
                                  sender_id INT NOT NULL,
                                  receiver_id INT NOT NULL,
                                  content TEXT NOT NULL,
                                  is_read TINYINT(1) DEFAULT 0,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE CASCADE,
                                  FOREIGN KEY (receiver_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 通知系统
CREATE TABLE notifications (
                               notification_id INT AUTO_INCREMENT PRIMARY KEY,
                               user_id INT NOT NULL,
                               type ENUM('SYSTEM','COMMENT','LIKE','FOLLOW','MENTION','MESSAGE') NOT NULL,
                               content JSON NOT NULL,
                               is_read TINYINT(1) NOT NULL DEFAULT 0,
                               create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               INDEX idx_notification_user (user_id, is_read),
                               FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 搜索历史表
CREATE TABLE search_history (
                                history_id INT AUTO_INCREMENT PRIMARY KEY,
                                user_id INT NOT NULL,
                                keyword VARCHAR(100) NOT NULL,
                                search_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                                INDEX idx_user_search (user_id, search_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 审核日志表
CREATE TABLE audit_logs (
                            log_id INT AUTO_INCREMENT PRIMARY KEY,
                            admin_id INT COMMENT '操作管理员（改为可空）',
                            action_type ENUM('USER','ARTICLE','COMMENT','TAG') NOT NULL COMMENT '操作对象类型',
                            target_id INT NOT NULL COMMENT '目标ID',
                            action ENUM('CREATE','UPDATE','DELETE','APPROVE','REJECT') NOT NULL,
                            details JSON COMMENT '变更详情',
                            remarks VARCHAR(255) COMMENT '备注',
                            create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (admin_id) REFERENCES admins(admin_id) ON DELETE SET NULL,
                            INDEX idx_audit_action (action_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 点赞触发器
DELIMITER //
CREATE TRIGGER after_article_like
    AFTER INSERT ON article_likes
    FOR EACH ROW
BEGIN
    UPDATE articles
    SET like_count = like_count + 1
    WHERE article_id = NEW.article_id;
END//
DELIMITER ;

-- 取消点赞触发器
DELIMITER //
CREATE TRIGGER after_article_unlike
    AFTER DELETE ON article_likes
    FOR EACH ROW
BEGIN
    UPDATE articles
    SET like_count = GREATEST(0, like_count - 1)
    WHERE article_id = OLD.article_id;
END//
DELIMITER ;

-- 添加评论触发器
DELIMITER //
CREATE TRIGGER after_comment_insert
    AFTER INSERT ON comments
    FOR EACH ROW
BEGIN
    IF NEW.target_type = 'ARTICLE' AND NEW.status = 'PUBLISHED' THEN
        UPDATE articles
        SET comment_count = comment_count + 1
        WHERE article_id = NEW.target_id;
    END IF;
END//
DELIMITER ;

-- 删除评论触发器
DELIMITER //
CREATE TRIGGER after_comment_delete
    AFTER DELETE ON comments
    FOR EACH ROW
BEGIN
    IF OLD.target_type = 'ARTICLE' AND OLD.status = 'PUBLISHED' THEN
        UPDATE articles
        SET comment_count = GREATEST(0, comment_count - 1)
        WHERE article_id = OLD.target_id;
    END IF;
END//
DELIMITER ;

-- 文章收藏触发器
DELIMITER //
CREATE TRIGGER after_article_collection
    AFTER INSERT ON article_collections
    FOR EACH ROW
BEGIN
    UPDATE articles
    SET collection_count = collection_count + 1
    WHERE article_id = NEW.article_id;
END//
DELIMITER ;

-- 取消收藏触发器
DELIMITER //
CREATE TRIGGER after_article_uncollection
    AFTER DELETE ON article_collections
    FOR EACH ROW
BEGIN
    UPDATE articles
    SET collection_count = GREATEST(0, collection_count - 1)
    WHERE article_id = OLD.article_id;
END//
DELIMITER ;

-- 文章阅读记录触发器
DELIMITER //
CREATE TRIGGER after_article_view
    AFTER INSERT ON article_view_records
    FOR EACH ROW
BEGIN
    -- 检查是否为同一用户24小时内的重复浏览
    IF NOT EXISTS (
        SELECT 1 FROM article_view_records
        WHERE article_id = NEW.article_id
          AND user_id = NEW.user_id
          AND view_time > DATE_SUB(NEW.view_time, INTERVAL 24 HOUR)
          AND id != NEW.id
    ) THEN
        UPDATE articles
        SET view_count = view_count + 1
        WHERE article_id = NEW.article_id;
    END IF;
END//
DELIMITER ;

-- 评论状态变更触发器
DELIMITER //
CREATE TRIGGER after_comment_update
    AFTER UPDATE ON comments
    FOR EACH ROW
BEGIN
    -- 评论状态从非发布变为发布
    IF NEW.target_type = 'ARTICLE' AND NEW.status = 'PUBLISHED' AND OLD.status != 'PUBLISHED' THEN
        UPDATE articles
        SET comment_count = comment_count + 1
        WHERE article_id = NEW.target_id;
        -- 评论状态从发布变为非发布
    ELSEIF NEW.target_type = 'ARTICLE' AND NEW.status != 'PUBLISHED' AND OLD.status = 'PUBLISHED' THEN
        UPDATE articles
        SET comment_count = GREATEST(0, comment_count - 1)
        WHERE article_id = NEW.target_id;
    END IF;
END//
DELIMITER ;

-- 优化后的评论回复计数器
DELIMITER //
CREATE TRIGGER after_reply_insert
    AFTER INSERT ON comments
    FOR EACH ROW
BEGIN
    IF NEW.target_type = 'COMMENT' AND NEW.status = 'PUBLISHED' THEN
        UPDATE comments
        SET reply_count = reply_count + 1
        WHERE comment_id = NEW.target_id;

        -- 同时设置父评论ID
        UPDATE comments
        SET parent_id = NEW.target_id
        WHERE comment_id = NEW.comment_id;
    END IF;
END//
DELIMITER ;