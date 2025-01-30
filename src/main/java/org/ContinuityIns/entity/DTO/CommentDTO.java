package org.ContinuityIns.entity.DTO;

import lombok.Data;
import org.ContinuityIns.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
    private Long commentId;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createTime;
    private Comment.CommentStatus status;
    private Long parentId;
    private Comment.TargetType targetType;
    private List<CommentDTO> replies;
}