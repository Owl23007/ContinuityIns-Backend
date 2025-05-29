package org.ContinuityIns.po;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessagePO {
    private Integer messageId;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private Integer isRead;
    private LocalDateTime createdAt;
}
