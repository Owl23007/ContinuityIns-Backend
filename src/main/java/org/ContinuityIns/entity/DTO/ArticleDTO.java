package org.ContinuityIns.entity.DTO;

import lombok.Data;
import org.ContinuityIns.entity.Article;

@Data
public class ArticleDTO {
    private Long articleId;
    private String title;
    private Long userId;
    private String nickname;
    private String content;
    private String coverImage;
    private Article.ArticleStatus status;
    private Integer duration;
    private String createTime;
}