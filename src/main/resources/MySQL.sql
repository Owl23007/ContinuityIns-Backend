-- Users Table
CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       nickname VARCHAR(255) NOT NULL,
                       signature VARCHAR(255) NOT NULL,
                       create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       avatar_image VARCHAR(255),
                       status ENUM('UNVERIFIED', 'NORMAL', 'BANNED', 'DEACTIVATED') NOT NULL DEFAULT 'UNVERIFIED',
                       encr_password VARCHAR(255) NOT NULL,
                       salt VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE
);

-- Articles Table
CREATE TABLE articles (
                          article_id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          user_id INT NOT NULL,
                          content TEXT NOT NULL,
                          cover_image VARCHAR(255),
                          create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          status ENUM('PUBLISHED', 'DRAFT', 'BANNED', 'PRIVATE') NOT NULL DEFAULT 'DRAFT',
                          duration INT NOT NULL DEFAULT 0,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Article View Records
CREATE TABLE article_view_records (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      article_id INT NOT NULL,
                                      user_id INT NOT NULL,
                                      view_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      is_starred BOOLEAN DEFAULT FALSE,
                                      is_liked BOOLEAN DEFAULT FALSE,
                                      is_ignored BOOLEAN DEFAULT FALSE,
                                      FOREIGN KEY (article_id) REFERENCES articles(article_id),
                                      FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Email Verification Tokens
CREATE TABLE user_tokens (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           user_id INT NOT NULL,
                                           token VARCHAR(255) NOT NULL,
                                           create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Videos Table
CREATE TABLE videos (
                        video_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        cover_image VARCHAR(255),
                        status ENUM('PUBLISHED', 'DRAFT', 'BANNED', 'PRIVATE') NOT NULL DEFAULT 'DRAFT',
                        video_url VARCHAR(255) NOT NULL,
                        duration INT NOT NULL,
                        description TEXT,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Video View Records
CREATE TABLE video_view_records (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    video_id INT NOT NULL,
                                    user_id INT NOT NULL,
                                    view_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    is_starred BOOLEAN DEFAULT FALSE,
                                    is_liked BOOLEAN DEFAULT FALSE,
                                    is_ignored BOOLEAN DEFAULT FALSE,
                                    FOREIGN KEY (video_id) REFERENCES videos(video_id),
                                    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Comments Table
CREATE TABLE comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          content TEXT NOT NULL,
                          create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          status ENUM('PUBLISHED', 'BANNED', 'DELETED') NOT NULL DEFAULT 'PUBLISHED',
                          target_id INT NOT NULL,
                          target_type ENUM('ARTICLE', 'VIDEO', 'COMMENT') NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Comment Likes
CREATE TABLE comment_likes (
                               like_id INT AUTO_INCREMENT PRIMARY KEY,
                               comment_id INT NOT NULL,
                               user_id INT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UNIQUE KEY unique_like (comment_id, user_id),
                               FOREIGN KEY (comment_id) REFERENCES comments(comment_id),
                               FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 索引
CREATE INDEX idx_articles_user ON articles(user_id);
CREATE INDEX idx_view_records ON article_view_records(user_id, article_id);