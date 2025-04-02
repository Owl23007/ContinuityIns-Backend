package org.ContinuityIns.DAO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagDAO {
    private int tagId;
    private String tagName;
    private int usrId; // 创建者id
    private TagStatus tagStatus;
    private LocalDateTime createTime;

    public enum TagStatus {
        NORMAL,
        BANNED,
    }
}
