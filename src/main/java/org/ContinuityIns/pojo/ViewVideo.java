package org.ContinuityIns.pojo;

import java.util.List;

public class ViewVideo extends PreviewVideo {
    private String description;
    private List<Comment> comments;

    public ViewVideo(Video video) {
        super(video);
    }
}
