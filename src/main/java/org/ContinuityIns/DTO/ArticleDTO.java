package org.ContinuityIns.DTO;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long articleId;
    private String title;
    private Long userId;
    private String nickname;
    private String content;
    private String coverImage;
    private ArticleStatus status;
    private Integer duration;
    private String createTime;

    enum ArticleStatus {
        PUBLISHED, DRAFT, BANNED, PRIVATE
    }
}