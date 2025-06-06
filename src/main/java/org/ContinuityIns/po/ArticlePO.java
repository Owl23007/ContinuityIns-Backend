package org.ContinuityIns.po;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ArticlePO {
    private Integer articleId;
    private Integer userId;
    private String title;
    private String content;
    private String summary;
    private String coverImage;
    private ArticleStatus status;
    private Integer wordCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer collectionCount;
    private Boolean isTop;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer categoryId;
    private String[] tags;

    public enum ArticleStatus {
        PUBLISHED, DRAFT, BANNED, PRIVATE, PENDING
    }
}
