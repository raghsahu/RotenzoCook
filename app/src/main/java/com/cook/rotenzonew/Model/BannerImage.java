package com.cook.rotenzonew.Model;

/**
 * Created by Raghvendra Sahu on 27/11/2019.
 */
public class BannerImage {
    String id;
    String image;


    public BannerImage(String id, String image) {
        this.id=id;
        this.image=image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
