package org.ContinuityIns.po;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentPO {
    private Long commentId;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createTime;
    private CommentStatus status;
    private Long parentId;
    private TargetType targetType;
    private List<CommentPO> replies;

    public enum CommentStatus {
        PUBLISHED, BANNED, DELETED
    }

    public enum TargetType {
        ARTICLE, VIDEO, COMMENT
    }
}
