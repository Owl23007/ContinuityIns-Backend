package org.ContinuityIns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchDTO {
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("type")
    private String type;
    @JsonProperty("page")
    private int page;
    @JsonProperty("pageSize")
    private int pageSize;
    @JsonProperty("sort")
    private String sort;
    @JsonProperty("category")
    private String category;
    @JsonProperty("tags")
    private List<String> tags;
    @JsonProperty("timeRange")
    private String timeRange;
    @JsonProperty("dateStart")
    private LocalDateTime dateStart;
    @JsonProperty("dateEnd")
    private LocalDateTime dateEnd;
}
