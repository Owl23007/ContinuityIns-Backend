package org.ContinuityIns.entity.DTO;

import lombok.Data;
import org.ContinuityIns.entity.Video;

@Data
public class VideoDTO {
    private Long videoId;
    private Long userId;
    private String nickname;
    private String title;
    private String coverImage;
    private Video.VideoStatus status;
    private Integer duration;
    private String description;
    private String createTime;
}