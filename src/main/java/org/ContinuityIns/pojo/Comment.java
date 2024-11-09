package org.ContinuityIns.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {
    private Integer commentId;
    private Integer userId;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Integer status;

    private Integer parentId;
    private Integer targetType;//1:文章 2:视频 3:评论
}



