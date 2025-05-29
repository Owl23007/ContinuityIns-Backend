package org.ContinuityIns.dto;

import lombok.Data;

@Data
public class ArticleCreateDTO {
    private String title;
    private String content;
    private String summary;
    private String coverImage;
    private Integer categoryId;
    private String[] tags;
}
