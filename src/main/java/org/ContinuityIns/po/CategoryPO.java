package org.ContinuityIns.po;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoryPO {
    private Integer categoryId;
    private String name;
    private Integer parentId;
    private Integer sortOrder;
    private String description;
    private LocalDateTime createTime;
}
