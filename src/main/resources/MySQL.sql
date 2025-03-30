-- 设置数据库默认字符集：utf8mb4
CREATE DATABASE IF NOT EXISTS ContinuityIns
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE ContinuityIns;

-- 用户表
CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       nickname VARCHAR(255) NOT NULL,
                       signature VARCHAR(255) NOT NULL,
                       avatar_image VARCHAR(255),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       encr_password VARCHAR(255) NOT NULL,
                       salt VARCHAR(255) NOT NULL,
                       status ENUM('UNVERIFIED', 'NORMAL', 'BANNED', 'DEACTIVATED') NOT NULL DEFAULT 'UNVERIFIED',
                       create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章表（增加审核机制，可以在业务逻辑中对 status 进行复合控制）
CREATE TABLE articles (
                          article_id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          user_id INT NOT NULL,
                          content TEXT NOT NULL,
                          cover_image VARCHAR(255),
                          status ENUM('DRAFT', 'PENDING', 'PUBLISHED', 'BANNED', 'PRIVATE') NOT NULL DEFAULT 'DRAFT',
                          duration INT NOT NULL DEFAULT 0,
                          create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_articles_user FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_articles_user ON articles(user_id);

-- 文章的浏览记录
CREATE TABLE article_view_records (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      article_id INT NOT NULL,
                                      user_id INT NOT NULL,
                                      view_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      is_starred BOOLEAN DEFAULT FALSE,
                                      is_liked BOOLEAN DEFAULT FALSE,
                                      is_ignored BOOLEAN DEFAULT FALSE,
                                      CONSTRAINT fk_avr_article FOREIGN KEY (article_id) REFERENCES articles(article_id),
                                      CONSTRAINT fk_avr_user FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_article_view ON article_view_records(user_id, article_id);

-- 邮箱验证令牌表
CREATE TABLE user_tokens (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             user_id INT NOT NULL,
                             token VARCHAR(255) NOT NULL,
                             create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT fk_user_tokens_user FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论表（增加待审核状态）
CREATE TABLE comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          content TEXT NOT NULL,
                          target_id INT NOT NULL,
                          target_type ENUM('ARTICLE', 'VIDEO', 'COMMENT') NOT NULL,
                          status ENUM('PENDING', 'PUBLISHED', 'BANNED', 'DELETED') NOT NULL DEFAULT 'PENDING',
                          create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论点赞表
CREATE TABLE comment_likes (
                               like_id INT AUTO_INCREMENT PRIMARY KEY,
                               comment_id INT NOT NULL,
                               user_id INT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UNIQUE KEY unique_like (comment_id, user_id),
                               CONSTRAINT fk_comment_likes_comment FOREIGN KEY (comment_id) REFERENCES comments(comment_id),
                               CONSTRAINT fk_comment_likes_user FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 审核日志表
CREATE TABLE audit_logs (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            table_name VARCHAR(64) NOT NULL COMMENT '操作的表名',
                            record_id INT NOT NULL COMMENT '被操作记录的主键',
                            action ENUM('INSERT', 'UPDATE', 'DELETE', 'APPROVE', 'REJECT') NOT NULL COMMENT '操作类型',
                            performed_by INT COMMENT '操作人（管理员或系统用户）',
                            remarks VARCHAR(255) COMMENT '备注信息',
                            performed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            INDEX idx_audit (table_name, record_id),
                            CONSTRAINT fk_audit_user FOREIGN KEY (performed_by) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;