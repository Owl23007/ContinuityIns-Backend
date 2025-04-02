package org.ContinuityIns.DAO;

import lombok.Data;

@Data
public class ArticleDAO {
    private Long articleId;
    private String title;
    private Integer userId;
    private String username;
    private String content;
    private String coverImage;
    private ArticleStatus status;
    private Integer duration;// 文章字数
    private String createTime;

    public enum ArticleStatus {
        PUBLISHED, DRAFT, BANNED, PRIVATE
    }
}