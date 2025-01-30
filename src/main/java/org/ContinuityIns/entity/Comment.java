package org.ContinuityIns.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(nullable = false)
    @NotNull(message = "评论内容不能为空")
    private String content;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus status = CommentStatus.发布;

    @Column(name = "parent_id", nullable = false)
    private Long parentId; // 合并后的字段，根据 targetType 解释为文章/视频/评论的 ID

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType; // 目标类型决定 parentId 的含义

    // 评论状态枚举
    public enum CommentStatus {
        发布, 封禁, 删除
    }

    // 目标类型枚举
    public enum TargetType {
        文章, 视频, 评论
    }

    public Comment() {}
}