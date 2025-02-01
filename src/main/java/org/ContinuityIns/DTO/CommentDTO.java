package org.ContinuityIns.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
    private Long commentId;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createTime;
    private CommentStatus status;
    private Long parentId;
    private TargetType targetType;
    private List<CommentDTO> replies;


    enum CommentStatus {
        PUBLISHED, BANNED, DELETED
    }

    enum TargetType {
        ARTICLE, VIDEO, COMMENT
    }
}

