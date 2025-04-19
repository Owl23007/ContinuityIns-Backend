package org.ContinuityIns.DAO;

import lombok.Data;

@Data
public class ArticleDAO {
    private Integer articleId;
    private String title;
    private Integer userId;
    private String nickname;
    private String avatarImage;
    private String content;
    private String coverImage;
    private ArticleStatus status;
    private Integer duration;// 文章字数
    private String createTime;

    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer collectionCount;

    public enum ArticleStatus {
        PUBLISHED, DRAFT, BANNED, PRIVATE
    }
}