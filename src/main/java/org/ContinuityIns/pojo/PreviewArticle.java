package org.ContinuityIns.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PreviewArticle extends Article {
        private String author;
        private Integer likeCount;
        private Integer viewCount;
        private Integer starCount;

        public PreviewArticle(Article article) {
            this.setArticleId(article.getArticleId());
            this.setTitle(article.getTitle());
            this.setUserId(article.getUserId());
            this.setContent(article.getContent());
            this.setDuration(article.getDuration());
            this.setCoverImage(article.getCoverImage());
            this.setStatus(article.getStatus());
            this.setCreateTime(article.getCreateTime());
            this.setUpdateTime(article.getUpdateTime());
        }
    }

