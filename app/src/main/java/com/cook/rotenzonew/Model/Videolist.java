package com.cook.rotenzonew.Model;

/**
 * Created by Raghvendra Sahu on 20/11/2019.
 */
public class Videolist {
    String video,video_title;

    public Videolist(String video, String video_title) {
        this.video = video;
        this.video_title = video_title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }
}
