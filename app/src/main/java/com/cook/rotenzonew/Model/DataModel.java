package com.cook.rotenzonew.Model;

public class DataModel {

    private String RestaurantName = "";
    private String Desc = "";
    private Integer imgSrc;

    public void setRestaurantName(String _RestaturantName) {
        this.RestaurantName = _RestaturantName;
    }
    public String getRestaurantName() {
        return this.RestaurantName;
    }

    public void setDesc(String _Desc) {
        this.Desc = _Desc;
    }
    public String getDesc() {
        return this.Desc;
    }

    public void setImgSrc(Integer _imgSrc) {
        this.imgSrc = _imgSrc;
    }
    public Integer getImgSrc() {
        return this.imgSrc;
    }
}
