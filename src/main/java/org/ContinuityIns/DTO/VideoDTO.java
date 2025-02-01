package org.ContinuityIns.DTO;

import lombok.Data;
import org.ContinuityIns.DTO.VideoDTO;

@Data
public class VideoDTO {
    private Long videoId;
    private Long userId;
    private String nickname;
    private String title;
    private String coverImage;
    private VideoStatus status;
    private Integer duration;
    private String description;
    private String createTime;

    public enum VideoStatus{
        PUBLISHED, DRAFT, BANNED, PRIVATE
    }
}