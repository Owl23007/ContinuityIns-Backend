package org.ContinuityIns.vo;

import lombok.Data;

@Data
public class ArticleVO {
    private Integer articleId;
    private String title;
    private String summary;
    private String coverImage;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer collectionCount;
    private Boolean isTop;
    private String[] tags;

}
