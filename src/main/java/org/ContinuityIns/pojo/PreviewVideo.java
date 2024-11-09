package org.ContinuityIns.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PreviewVideo extends Video {
    private String author;
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;
    private Integer starCount;

    public PreviewVideo(Video video) {
        this.setVideoId(video.getVideoId());
        this.setUserId(video.getUserId());
        this.setTitle(video.getTitle());
        this.setCreateTime(video.getCreateTime());
        this.setUpdateTime(video.getUpdateTime());
        this.setCoverImage(video.getCoverImage());
        this.setVideoUrl(video.getVideoUrl());
        this.setStatus(video.getStatus());
        this.setDuration(video.getDuration());
    }
}
