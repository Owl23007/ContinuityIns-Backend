package org.ContinuityIns.po;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TagPO {
    private int tagId;
    private String tagName;
    private int usrId;
    private TagStatus tagStatus;
    private LocalDateTime createTime;

    public enum TagStatus {
        NORMAL,
        BANNED,
    }
}
