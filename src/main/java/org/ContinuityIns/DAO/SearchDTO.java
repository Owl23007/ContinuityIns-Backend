package org.ContinuityIns.DAO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchDTO {
    private String keyword;
    private String type;
    private Integer page = 1;
    private Integer pageSize = 10;
    private String sort;
    private String category;
    private List<String> tags;
    private String timeRange;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
}
